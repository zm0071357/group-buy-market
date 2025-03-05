package group.buy.market.api;

import group.buy.market.api.dto.LockMarketPayOrderRequestDTO;
import group.buy.market.api.dto.LockMarketPayOrderResponseDTO;
import group.buy.market.api.dto.SettlementMarketPayOrderRequestDTO;
import group.buy.market.api.dto.SettlementMarketPayOrderResponseDTO;
import group.buy.market.api.response.Response;

/**
 * 营销交易服务
 */
public interface MarketTradeService {

    /**
     * 锁单
     * @param lockMarketPayOrderRequestDTO
     * @return
     */
    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO);

    /**
     * 结算
     * @param settlementMarketPayOrderRequestDTO
     * @return
     */
    Response<SettlementMarketPayOrderResponseDTO> settlementMarketPayOrder(SettlementMarketPayOrderRequestDTO settlementMarketPayOrderRequestDTO);
}
