package group.buy.market.domain.trade.service.settlement;

import group.buy.market.domain.trade.model.entity.TradePaySettlementEntity;
import group.buy.market.domain.trade.model.entity.TradePaySuccessEntity;

/**
 * 拼团交易结算
 */
public interface TradeSettlementOrderService {

    TradePaySettlementEntity settlementMarketPayOrder(TradePaySuccessEntity tradePaySuccessEntity) throws Exception;

}
