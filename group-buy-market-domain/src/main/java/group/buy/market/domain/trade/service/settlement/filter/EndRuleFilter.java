package group.buy.market.domain.trade.service.settlement.filter;

import group.buy.market.domain.trade.model.entity.GroupBuyTeamEntity;
import group.buy.market.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import group.buy.market.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import group.buy.market.domain.trade.service.settlement.factory.TradeSettlementRuleFilterFactory;
import group.buy.market.types.design.framework.link.model2.handler.LogicHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 结束节点
 */
@Slf4j
@Service
public class EndRuleFilter implements LogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-结束节点{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());
        GroupBuyTeamEntity groupBuyTeamEntity = dynamicContext.getGroupBuyTeamEntity();
        return TradeSettlementRuleFilterBackEntity.builder()
                .teamId(groupBuyTeamEntity.getTeamId())
                .activityId(groupBuyTeamEntity.getActivityId())
                .targetCount(groupBuyTeamEntity.getTargetCount())
                .completeCount(groupBuyTeamEntity.getCompleteCount())
                .lockCount(groupBuyTeamEntity.getLockCount())
                .status(groupBuyTeamEntity.getStatus())
                .validStartTime(groupBuyTeamEntity.getValidStartTime())
                .validEndTime(groupBuyTeamEntity.getValidEndTime())
                .build();
    }

}
