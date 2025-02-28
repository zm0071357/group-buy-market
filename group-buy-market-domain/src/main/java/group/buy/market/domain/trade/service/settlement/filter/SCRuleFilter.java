package group.buy.market.domain.trade.service.settlement.filter;

import group.buy.market.domain.trade.adapter.repository.TradeRepository;
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
 * SC 渠道来源过滤 - 当某个签约渠道下架后，则不会记账
 */
@Slf4j
@Service
public class SCRuleFilter implements LogicHandler<TradeSettlementRuleCommandEntity, TradeSettlementRuleFilterFactory.DynamicContext, TradeSettlementRuleFilterBackEntity> {

    @Resource
    private TradeRepository tradeRepository;

    @Override
    public TradeSettlementRuleFilterBackEntity apply(TradeSettlementRuleCommandEntity requestParameter, TradeSettlementRuleFilterFactory.DynamicContext dynamicContext) throws Exception {
        log.info("结算规则过滤-渠道黑名单校验{} outTradeNo:{}", requestParameter.getUserId(), requestParameter.getOutTradeNo());

        // sc 渠道黑名单拦截
        boolean intercept = tradeRepository.isSCBlackIntercept(requestParameter.getSource(), requestParameter.getChannel());
        if (intercept) {
            log.error("{}{} 渠道黑名单拦截", requestParameter.getSource(), requestParameter.getChannel());
            throw new AppException(ResponseCode.E0105);
        }

        return next(requestParameter, dynamicContext);
    }

}
