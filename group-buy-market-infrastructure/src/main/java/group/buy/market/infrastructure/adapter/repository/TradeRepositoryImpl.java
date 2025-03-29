package group.buy.market.infrastructure.adapter.repository;

import com.alibaba.fastjson.JSON;
import group.buy.market.domain.trade.adapter.repository.TradeRepository;
import group.buy.market.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import group.buy.market.domain.trade.model.aggregate.GroupBuyTeamAggregate;
import group.buy.market.domain.trade.model.entity.*;
import group.buy.market.domain.trade.model.valobj.GroupBuyProgressVO;
import group.buy.market.domain.trade.model.valobj.TradeOrderStatusEnum;
import group.buy.market.infrastructure.dao.GroupBuyActivityDao;
import group.buy.market.infrastructure.dao.GroupBuyOrderDao;
import group.buy.market.infrastructure.dao.GroupBuyOrderListDao;
import group.buy.market.infrastructure.dao.NotifyTaskDao;
import group.buy.market.infrastructure.dao.po.GroupBuyActivity;
import group.buy.market.infrastructure.dao.po.GroupBuyOrder;
import group.buy.market.infrastructure.dao.po.GroupBuyOrderList;
import group.buy.market.infrastructure.dao.po.NotifyTask;
import group.buy.market.infrastructure.dcc.DccServiceImpl;
import group.buy.market.types.common.Constants;
import group.buy.market.types.enums.ActivityStatusEnumVO;
import group.buy.market.types.enums.GroupBuyOrderEnumVO;
import group.buy.market.types.enums.ResponseCode;
import group.buy.market.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class TradeRepositoryImpl implements TradeRepository {

    @Resource
    private GroupBuyOrderDao groupBuyOrderDao;

    @Resource
    private GroupBuyOrderListDao groupBuyOrderListDao;

    @Resource
    private GroupBuyActivityDao groupBuyActivityDao;

    @Resource
    private NotifyTaskDao notifyTaskDao;

    @Resource
    private DccServiceImpl dccServiceImpl;

    @Override
    public MarketPayOrderEntity queryNoPayMarketPayOrderByOutTradeNo(String userId, String outTradeNo) {
        GroupBuyOrderList groupBuyOrderReq = new GroupBuyOrderList();
        groupBuyOrderReq.setUserId(userId);
        groupBuyOrderReq.setOutTradeNo(outTradeNo);
        GroupBuyOrderList groupBuyOrderList = groupBuyOrderListDao.queryGroupBuyOrderRecordByOutTradeNo(groupBuyOrderReq);
        if (groupBuyOrderList == null) {
            return null;
        }
        return MarketPayOrderEntity.builder()
                .teamId(groupBuyOrderList.getTeamId())
                .orderId(groupBuyOrderList.getOrderId())
                .originalPrice(groupBuyOrderList.getOriginalPrice())
                .payPrice(groupBuyOrderList.getPayPrice())
                .deductionPrice(groupBuyOrderList.getDeductionPrice())
                .tradeOrderStatusEnumVO(TradeOrderStatusEnum.valueOf(groupBuyOrderList.getStatus()))
                .build();
    }

    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        GroupBuyOrder groupBuyOrder = groupBuyOrderDao.queryGroupBuyProgress(teamId);
        if (groupBuyOrder == null) {
            return null;
        }
        return GroupBuyProgressVO.builder()
                .completeCount(groupBuyOrder.getCompleteCount())
                .lockCount(groupBuyOrder.getLockCount())
                .targetCount(groupBuyOrder.getTargetCount())
                .build();
    }

    @Transactional(timeout = 500)
    @Override
    public MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate) {
        PayActivityEntity payActivityEntity = groupBuyOrderAggregate.getPayActivityEntity();
        PayDiscountEntity payDiscountEntity = groupBuyOrderAggregate.getPayDiscountEntity();
        UserEntity userEntity = groupBuyOrderAggregate.getUserEntity();
        Integer userTakeOrderCount = groupBuyOrderAggregate.getUserTakeOrderCount();

        // 拼团不存在 新建一个团
        String teamId = payActivityEntity.getTeamId();

        // 计算有效日期
        Date currentTime = new Date();
        Calendar calender = Calendar.getInstance();
        calender.setTime(currentTime);
        calender.add(Calendar.MINUTE, payActivityEntity.getValidTime());

        if (StringUtils.isBlank(teamId)) {
            teamId = RandomStringUtils.randomNumeric(8);
            // 构建拼团订单
            GroupBuyOrder groupBuyOrder = GroupBuyOrder.builder()
                    .teamId(teamId)
                    .activityId(payActivityEntity.getActivityId())
                    .source(payDiscountEntity.getSource())
                    .channel(payDiscountEntity.getChannel())
                    .originalPrice(payDiscountEntity.getOriginalPrice())
                    .deductionPrice(payDiscountEntity.getDeductionPrice())
                    .payPrice(payDiscountEntity.getPayPrice())
                    .targetCount(payActivityEntity.getTargetCount())
                    .completeCount(0)
                    .lockCount(1)
                    .validStartTime(currentTime)
                    .validEndTime(calender.getTime())
                    .notifyUrl(payDiscountEntity.getNotifyUrl())
                    .build();
            groupBuyOrderDao.insert(groupBuyOrder);
        // 拼团存在
        } else {
            // 更新锁单数
            int updateAddLockCount = groupBuyOrderDao.updateAddLockCount(teamId);
            if (updateAddLockCount != 1) {
                throw new AppException(ResponseCode.E0005.getCode(), ResponseCode.E0005.getInfo());
            }
        }

        // 创建用户拼团明细
        String orderId = "PT" + RandomStringUtils.randomNumeric(12);
        GroupBuyOrderList groupBuyOrderListReq = GroupBuyOrderList.builder()
                .userId(userEntity.getUserId())
                .teamId(teamId)
                .orderId(orderId)
                .activityId(payActivityEntity.getActivityId())
                .startTime(payActivityEntity.getStartTime())
                .endTime(payActivityEntity.getEndTime())
                .goodsId(payDiscountEntity.getGoodsId())
                .source(payDiscountEntity.getSource())
                .channel(payDiscountEntity.getChannel())
                .originalPrice(payDiscountEntity.getOriginalPrice())
                .deductionPrice(payDiscountEntity.getDeductionPrice())
                .payPrice(payDiscountEntity.getPayPrice())
                .status(TradeOrderStatusEnum.CREATE.getCode())
                .outTradeNo(payDiscountEntity.getOutTradeNo())
                .bizId(payActivityEntity.getActivityId() + Constants.UNDERLINE + userEntity.getUserId() + Constants.UNDERLINE + (userTakeOrderCount + 1))
                .build();
        try {
            // 写入拼团记录
            groupBuyOrderListDao.insert(groupBuyOrderListReq);
        } catch (DuplicateKeyException e) {
            throw new AppException(ResponseCode.INDEX_EXCEPTION);
        }

        return MarketPayOrderEntity.builder()
                .teamId(teamId)
                .orderId(orderId)
                .payPrice(payDiscountEntity.getPayPrice())
                .originalPrice(payDiscountEntity.getOriginalPrice())
                .deductionPrice(payDiscountEntity.getDeductionPrice())
                .tradeOrderStatusEnumVO(TradeOrderStatusEnum.CREATE)
                .build();

    }

    @Override
    public GroupBuyActivityEntity queryGroupBuyActivityByActivityId(Long activityId) {
        GroupBuyActivity groupBuyActivity = groupBuyActivityDao.queryGroupBuyActivityByActivityId(activityId);
        return GroupBuyActivityEntity.builder()
                .activityId(groupBuyActivity.getActivityId())
                .activityName(groupBuyActivity.getActivityName())
                .discountId(groupBuyActivity.getDiscountId())
                .groupType(groupBuyActivity.getGroupType())
                .takeLimitCount(groupBuyActivity.getTakeLimitCount())
                .target(groupBuyActivity.getTarget())
                .validTime(groupBuyActivity.getValidTime())
                .status(ActivityStatusEnumVO.valueOf(groupBuyActivity.getStatus()))
                .startTime(groupBuyActivity.getStartTime())
                .endTime(groupBuyActivity.getEndTime())
                .tagId(groupBuyActivity.getTagId())
                .tagScope(groupBuyActivity.getTagScope())
                .build();
    }

    @Override
    public Integer queryOrderCount(Long activityId, String userId) {
        GroupBuyOrderList groupBuyOrderListReq = new GroupBuyOrderList();
        groupBuyOrderListReq.setActivityId(activityId);
        groupBuyOrderListReq.setUserId(userId);
        return groupBuyOrderListDao.queryOrderCount(groupBuyOrderListReq);
    }

    @Override
    public GroupBuyTeamEntity queryGroupBuyTeamByTeamId(String teamId) {
        GroupBuyOrder groupBuyOrder = groupBuyOrderDao.queryGroupBuyTeamByTeamId(teamId);
        return GroupBuyTeamEntity.builder()
                .teamId(groupBuyOrder.getTeamId())
                .activityId(groupBuyOrder.getActivityId())
                .targetCount(groupBuyOrder.getTargetCount())
                .completeCount(groupBuyOrder.getCompleteCount())
                .lockCount(groupBuyOrder.getLockCount())
                .status(GroupBuyOrderEnumVO.valueOf(groupBuyOrder.getStatus()))
                .validStartTime(groupBuyOrder.getValidStartTime())
                .validEndTime(groupBuyOrder.getValidEndTime())
                .notifyUrl(groupBuyOrder.getNotifyUrl())
                .build();
    }

    @Transactional(timeout = 500)
    @Override
    public void settlementMarketPayOrder(GroupBuyTeamAggregate groupBuyTeamAggregate) {
        UserEntity userEntity = groupBuyTeamAggregate.getUserEntity();
        GroupBuyTeamEntity groupBuyTeamEntity = groupBuyTeamAggregate.getGroupBuyTeamEntity();
        TradePaySuccessEntity tradePaySuccessEntity = groupBuyTeamAggregate.getTradePaySuccessEntity();

        GroupBuyOrderList groupBuyOrderListReq = new GroupBuyOrderList();
        groupBuyOrderListReq.setUserId(userEntity.getUserId());
        groupBuyOrderListReq.setOutTradeNo(tradePaySuccessEntity.getOutTradeNo());
        groupBuyOrderListReq.setOutTradeTime(tradePaySuccessEntity.getOutTradeTime());

        // 更新订单状态
        int updateOrderListStatusCount = groupBuyOrderListDao.updateOrderStatusComplete(groupBuyOrderListReq);
        if (updateOrderListStatusCount != 1) {
            throw new AppException(ResponseCode.UPDATE_ZERO.getCode(), ResponseCode.UPDATE_ZERO.getInfo());
        }

        // 更新拼团进度
        int updateAddCount = groupBuyOrderDao.updateAddCompleteCount(groupBuyTeamEntity.getTeamId());
        if (updateAddCount != 1) {
            throw new AppException(ResponseCode.UPDATE_ZERO.getCode(), ResponseCode.UPDATE_ZERO.getInfo());
        }

        // 最后一笔
        if (groupBuyTeamEntity.getTargetCount() - groupBuyTeamEntity.getCompleteCount() == 1) {
            // 拼团目标完成
            int updateTeamCompleteCount = groupBuyOrderDao.updateTeamComplete(groupBuyTeamEntity.getTeamId());
            if (updateTeamCompleteCount != 1) {
                throw new AppException(ResponseCode.UPDATE_ZERO.getCode(), ResponseCode.UPDATE_ZERO.getInfo());
            }

            List<String> outTradeNoList = groupBuyOrderListDao.queryGroupBuyCompleteOrderOutTradeNoListByTeamId(groupBuyTeamEntity.getTeamId());

            NotifyTask notifyTask = new NotifyTask();
            notifyTask.setActivityId(groupBuyTeamEntity.getActivityId());
            notifyTask.setTeamId(groupBuyTeamEntity.getTeamId());
            notifyTask.setNotifyUrl(groupBuyTeamEntity.getNotifyUrl());
            notifyTask.setNotifyCount(0);
            notifyTask.setNotifyStatus(0);
            notifyTask.setParameterJson(JSON.toJSONString(new HashMap<String, Object>(){{
                put("teamId", groupBuyTeamEntity.getTeamId());
                put("outTradeNoList", outTradeNoList);
            }}));

            notifyTaskDao.insert(notifyTask);
        }

    }

    @Override
    public boolean isSCBlackIntercept(String source, String channel) {
        return dccServiceImpl.isScBlackList(source, channel);
    }

    @Override
    public List<NotifyTaskEntity> queryUnExecutedNotifyTaskList() {
        List<NotifyTask> notifyTaskList = notifyTaskDao.queryUnExecutedNotifyTaskList();
        if (notifyTaskList.isEmpty()) {
            return new ArrayList<>();
        }
        List<NotifyTaskEntity> notifyTaskEntities = new ArrayList<>();
        for (NotifyTask notifyTask : notifyTaskList) {
            NotifyTaskEntity notifyTaskEntity = NotifyTaskEntity.builder()
                    .teamId(notifyTask.getTeamId())
                    .notifyUrl(notifyTask.getNotifyUrl())
                    .notifyCount(notifyTask.getNotifyCount())
                    .parameterJson(notifyTask.getParameterJson())
                    .build();
            notifyTaskEntities.add(notifyTaskEntity);
        }
        return notifyTaskEntities;
    }

    @Override
    public List<NotifyTaskEntity> queryUnExecutedNotifyTaskList(String teamId) {
        NotifyTask notifyTask = notifyTaskDao.queryUnExecutedNotifyTaskByTeamId(teamId);
        if (notifyTask == null){
            return new ArrayList<>();
        }
        return Collections.singletonList(NotifyTaskEntity.builder()
                .teamId(notifyTask.getTeamId())
                .notifyUrl(notifyTask.getNotifyUrl())
                .notifyCount(notifyTask.getNotifyCount())
                .parameterJson(notifyTask.getParameterJson())
                .build());

    }

    @Override
    public int updateNotifyTaskStatusSuccess(String teamId) {
        return notifyTaskDao.updateNotifyTaskStatusSuccess(teamId);
    }

    @Override
    public int updateNotifyTaskStatusError(String teamId) {
        return notifyTaskDao.updateNotifyTaskStatusError(teamId);
    }

    @Override
    public int updateNotifyTaskStatusRetry(String teamId) {
        return notifyTaskDao.updateNotifyTaskStatusRetry(teamId);
    }
}
