package group.buy.market.domain.trade.service.settlement;

import group.buy.market.domain.trade.model.entity.NotifyTaskEntity;
import group.buy.market.domain.trade.model.entity.TradePaySettlementEntity;
import group.buy.market.domain.trade.model.entity.TradePaySuccessEntity;

import java.util.Map;

/**
 * 拼团交易结算
 */
public interface TradeSettlementOrderService {

    /**
     * 支付订单结算
     * @param tradePaySuccessEntity
     * @return
     * @throws Exception
     */
    TradePaySettlementEntity settlementMarketPayOrder(TradePaySuccessEntity tradePaySuccessEntity) throws Exception;

    /**
     * 回调任务
     * @return
     * @throws Exception
     */
    Map<String, Integer> execSettlementNotifyJob() throws Exception;

    /**
     * 回调任务
     * @param teamId
     * @return
     * @throws Exception
     */
    Map<String, Integer> execSettlementNotifyJob(String teamId) throws Exception;

    /**
     * 回调任务
     * @param notifyTaskEntity
     * @return
     * @throws Exception
     */
    Map<String, Integer> execSettlementNotifyJob(NotifyTaskEntity notifyTaskEntity) throws Exception;

}
