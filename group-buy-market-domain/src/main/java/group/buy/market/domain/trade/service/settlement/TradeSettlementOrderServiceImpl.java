package group.buy.market.domain.trade.service.settlement;

import com.alibaba.fastjson.JSON;
import group.buy.market.domain.trade.adapter.port.TradePortService;
import group.buy.market.domain.trade.adapter.repository.TradeRepository;
import group.buy.market.domain.trade.model.aggregate.GroupBuyTeamAggregate;
import group.buy.market.domain.trade.model.entity.*;
import group.buy.market.domain.trade.service.settlement.factory.TradeSettlementRuleFilterFactory;
import group.buy.market.types.design.framework.link.model2.chain.BusinessLinkedList;
import group.buy.market.types.design.framework.link.model2.handler.LogicHandler;
import group.buy.market.types.enums.NotifyTaskHttpEnumVO;
import group.buy.market.types.enums.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TradeSettlementOrderServiceImpl implements TradeSettlementOrderService {

    @Resource
    private TradeRepository tradeRepository;

    @Resource
    private TradePortService tradePortService;

    @Resource
    private BusinessLinkedList<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> tradeSettlementRuleFilter;

    @Override
    public TradePaySettlementEntity settlementMarketPayOrder(TradePaySuccessEntity tradePaySuccessEntity) throws Exception {
        log.info("拼团交易-支付订单结算:{} outTradeNo:{}", tradePaySuccessEntity.getUserId(), tradePaySuccessEntity.getOutTradeNo());
        // 结算规则过滤
        TradeSettlementRuleFilterBackEntity tradeSettlementRuleFilterBackEntity = tradeSettlementRuleFilter.apply(
                TradeSettlementRuleCommandEntity.builder()
                        .source(tradePaySuccessEntity.getSource())
                        .channel(tradePaySuccessEntity.getChannel())
                        .userId(tradePaySuccessEntity.getUserId())
                        .outTradeNo(tradePaySuccessEntity.getOutTradeNo())
                        .outTradeTime(tradePaySuccessEntity.getOutTradeTime())
                        .build(),
                new TradeSettlementRuleFilterFactory.DynamicContext());

        String teamId = tradeSettlementRuleFilterBackEntity.getTeamId();

        // 查询组团信息
        GroupBuyTeamEntity groupBuyTeamEntity = GroupBuyTeamEntity.builder()
                .teamId(tradeSettlementRuleFilterBackEntity.getTeamId())
                .activityId(tradeSettlementRuleFilterBackEntity.getActivityId())
                .targetCount(tradeSettlementRuleFilterBackEntity.getTargetCount())
                .completeCount(tradeSettlementRuleFilterBackEntity.getCompleteCount())
                .lockCount(tradeSettlementRuleFilterBackEntity.getLockCount())
                .status(tradeSettlementRuleFilterBackEntity.getStatus())
                .validStartTime(tradeSettlementRuleFilterBackEntity.getValidStartTime())
                .validEndTime(tradeSettlementRuleFilterBackEntity.getValidEndTime())
                .notifyUrl(tradeSettlementRuleFilterBackEntity.getNotifyUrl())
                .build();

        // 构建聚合对象
        GroupBuyTeamAggregate groupBuyTeamAggregate = GroupBuyTeamAggregate.builder()
                .userEntity(UserEntity.builder().userId(tradePaySuccessEntity.getUserId()).build())
                .groupBuyTeamEntity(groupBuyTeamEntity)
                .tradePaySuccessEntity(tradePaySuccessEntity)
                .build();

        // 拼团交易结算
        tradeRepository.settlementMarketPayOrder(groupBuyTeamAggregate);

        // 组队回调处理
        Map<String, Integer> result = execSettlementNotifyJob(teamId);
        log.info("回调通知拼团完成 result:{}", JSON.toJSONString(result));

        // 返回结算信息
        return TradePaySettlementEntity.builder()
                .source(tradePaySuccessEntity.getSource())
                .channel(tradePaySuccessEntity.getChannel())
                .userId(tradePaySuccessEntity.getUserId())
                .teamId(teamId)
                .activityId(groupBuyTeamEntity.getActivityId())
                .outTradeNo(tradePaySuccessEntity.getOutTradeNo())
                .build();
    }

    @Override
    public Map<String, Integer> execSettlementNotifyJob() throws Exception {
        log.info("拼团交易-执行结算通知任务");
        // 查询没有被执行的回调任务集合
        List<NotifyTaskEntity> notifyTaskEntityList = tradeRepository.queryUnExecutedNotifyTaskList();

        return getStringIntegerMap(notifyTaskEntityList);
    }

    private Map<String, Integer> getStringIntegerMap(List<NotifyTaskEntity> notifyTaskEntityList) throws Exception {
        int successCount = 0;
        int errorCount = 0;
        int retryCount = 0;

        // 执行回调任务
        for (NotifyTaskEntity notifyTaskEntity : notifyTaskEntityList) {
            String response = tradePortService.groupBuyNotify(notifyTaskEntity);
            // 执行成功
            if (response.equals(NotifyTaskHttpEnumVO.SUCCESS.getCode())) {
                // 更新数据库状态
                int updateCount = tradeRepository.updateNotifyTaskStatusSuccess(notifyTaskEntity.getTeamId());
                if (updateCount == 1) {
                    successCount ++;
                }
            // 执行失败
            } else if (response.equals(NotifyTaskHttpEnumVO.ERROR.getCode())) {
                // 失败
                if (notifyTaskEntity.getNotifyCount() < 5) {
                    int updateCount = tradeRepository.updateNotifyTaskStatusError(notifyTaskEntity.getTeamId());
                    if (updateCount == 1) {
                        errorCount ++;
                    }
                // 失败五次后重试
                } else {
                    int updateCount = tradeRepository.updateNotifyTaskStatusRetry(notifyTaskEntity.getTeamId());
                    if (updateCount == 1) {
                        retryCount ++;
                    }
                }
            }
        }
        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("waitCount", notifyTaskEntityList.size());
        resultMap.put("successCount", successCount);
        resultMap.put("errorCount", errorCount);
        resultMap.put("retryCount", retryCount);
        return resultMap;
    }

    @Override
    public Map<String, Integer> execSettlementNotifyJob(String teamId) throws Exception {
        log.info("拼团交易-执行结算通知回调，指定 teamId:{}", teamId);
        List<NotifyTaskEntity> notifyTaskEntityList = tradeRepository.queryUnExecutedNotifyTaskList(teamId);
        return getStringIntegerMap(notifyTaskEntityList);
    }
}
