package group.buy.market.infrastructure.adapter.repository;

import group.buy.market.domain.trade.adapter.repository.TradeRepository;
import group.buy.market.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import group.buy.market.domain.trade.model.entity.MarketPayOrderEntity;
import group.buy.market.domain.trade.model.entity.PayActivityEntity;
import group.buy.market.domain.trade.model.entity.PayDiscountEntity;
import group.buy.market.domain.trade.model.entity.UserEntity;
import group.buy.market.domain.trade.model.valobj.GroupBuyProgressVO;
import group.buy.market.domain.trade.model.valobj.TradeOrderStatusEnum;
import group.buy.market.infrastructure.dao.GroupBuyOrderDao;
import group.buy.market.infrastructure.dao.GroupBuyOrderListDao;
import group.buy.market.infrastructure.dao.po.GroupBuyOrder;
import group.buy.market.infrastructure.dao.po.GroupBuyOrderList;
import group.buy.market.types.enums.ResponseCode;
import group.buy.market.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class TradeRepositoryImpl implements TradeRepository {

    @Resource
    private GroupBuyOrderDao groupBuyOrderDao;

    @Resource
    private GroupBuyOrderListDao groupBuyOrderListDao;

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
                .orderId(groupBuyOrderList.getOrderId())
                .deductionPrice(groupBuyOrderList.getDeductionPrice())
                .tradeOrderStatusEnumVO(TradeOrderStatusEnum.valueOf(groupBuyOrderList.getStatus()))
                .build();
    }

    @Override
    public GroupBuyProgressVO queryGroupBuyProgress(String teamId) {
        // 查询拼团信息 - 获取拼团进度：目标量、完成量、锁单量
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

        String teamId = payActivityEntity.getTeamId();
        // 拼团不存在 - 新建一个团
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
                    .payPrice(payDiscountEntity.getDeductionPrice())
                    .targetCount(payActivityEntity.getTargetCount())
                    .completeCount(0)
                    .lockCount(1)
                    .build();
            // 插入数据库
            groupBuyOrderDao.insert(groupBuyOrder);
        // 拼团存在
        } else {
            // 更新锁单量
            int updateAddLockCount = groupBuyOrderDao.updateAddLockCount(teamId);
            // 更新失败 - 抛异常
            if (updateAddLockCount != 1) {
                throw new AppException(ResponseCode.E0005.getCode(), ResponseCode.E0005.getInfo());
            }
        }

        // 创建用户拼团信息
        // 订单ID - 拼团系统
        String orderId = RandomStringUtils.randomAlphabetic(12);
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
                .status(TradeOrderStatusEnum.CREATE.getCode())
                .outTradeNo(payDiscountEntity.getOutTradeNo())
                .build();
        try {
            // 插入数据库
            groupBuyOrderListDao.insert(groupBuyOrderListReq);
        } catch (DuplicateKeyException e) {
            // 唯一索引冲突
            throw new AppException(ResponseCode.INDEX_EXCEPTION);
        }

        return MarketPayOrderEntity.builder()
                .orderId(orderId)
                .deductionPrice(payDiscountEntity.getDeductionPrice())
                .tradeOrderStatusEnumVO(TradeOrderStatusEnum.CREATE)
                .build();
    }
}
