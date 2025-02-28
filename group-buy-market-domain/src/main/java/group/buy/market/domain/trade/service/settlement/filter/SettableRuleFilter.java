package group.buy.market.domain.trade.service.settlement.filter;

import group.buy.market.domain.trade.adapter.repository.TradeRepository;
import group.buy.market.domain.trade.model.entity.GroupBuyTeamEntity;
import group.buy.market.domain.trade.model.entity.MarketPayOrderEntity;
import group.buy.market.domain.trade.model.entity.TradeSettlementRuleCommandEntity;
import group.buy.market.domain.trade.model.entity.TradeSettlementRuleFilterBackEntity;
import group.buy.market.domain.trade.service.settlement.factory.TradeSettlementRuleFilterFactory;
import group.buy.market.types.design.framework.link.model2.handler.LogicHandler;
import group.buy.market.types.enums.ResponseCode;
import group.buy.market.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 可结算规则过滤；交易时间
 */
@Slf4j
@Service
public class SettableRuleFilter implements LogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Resource
    private TradeRepository tradeRepository;

    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-有效时间校验{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());
        MarketPayOrderEntity marketPayOrderEntity = dynamicContext.getMarketPayOrderEntity();
        // 查询拼团对象
        GroupBuyTeamEntity groupBuyTeamEntity = tradeRepository.queryGroupBuyTeamByTeamId(marketPayOrderEntity.getTeamId());
        // 外部交易时间 - 用户支付完成的时间，这个时间要在拼团有效时间范围内
        Date outTradeTime = requestParameter.getOutTradeTime();
        // 外部交易时间要小于拼团结束时间
        if (!outTradeTime.before(groupBuyTeamEntity.getValidEndTime())) {
            log.error("订单交易时间不在拼团有效时间范围内");
            throw new AppException(ResponseCode.E0106);
        }
        dynamicContext.setGroupBuyTeamEntity(groupBuyTeamEntity);
        return next(requestParameter, dynamicContext);
    }

}
