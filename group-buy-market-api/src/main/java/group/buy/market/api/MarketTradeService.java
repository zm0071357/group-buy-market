package group.buy.market.api;

import group.buy.market.api.dto.LockMarketPayOrderRequestDTO;
import group.buy.market.api.dto.LockMarketPayOrderResponseDTO;
import group.buy.market.api.response.Response;

public interface MarketTradeService {
    Response<LockMarketPayOrderResponseDTO> lockMarketPayOrder(LockMarketPayOrderRequestDTO lockMarketPayOrderRequestDTO);
}
