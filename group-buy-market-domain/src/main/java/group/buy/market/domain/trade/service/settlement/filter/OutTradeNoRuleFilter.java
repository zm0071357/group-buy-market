package group.buy.market.domain.trade.service.settlement.filter;

import group.buy.market.domain.trade.adapter.repository.TradeRepository;
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

/**
 * 外部交易单号过滤；外部交易单号是否有效
 */
@Slf4j
@Service
public class OutTradeNoRuleFilter implements LogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Resource
    private TradeRepository tradeRepository;

    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-外部单号校验{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());

        // 查询拼团信息
        MarketPayOrderEntity marketPayOrderEntity = tradeRepository.queryNoPayMarketPayOrderByOutTradeNo(requestParameter.getUserId(), requestParameter.getOutTradeNo());
        if (marketPayOrderEntity == null) {
            log.error("不存在的外部交易单号或用户已退单，不需要做支付订单结算:{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());
            throw new AppException(ResponseCode.E0104);
        }
        dynamicContext.setMarketPayOrderEntity(marketPayOrderEntity);
        return next(requestParameter, dynamicContext);
    }

}
