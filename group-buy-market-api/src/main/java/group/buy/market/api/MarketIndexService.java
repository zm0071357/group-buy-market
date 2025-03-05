package group.buy.market.api;

import group.buy.market.api.dto.GoodsMarketRequestDTO;
import group.buy.market.api.dto.GoodsMarketResponseDTO;
import group.buy.market.api.response.Response;

/**
 * 营销首页服务
 */
public interface MarketIndexService {

    /**
     * 查询拼团营销信息
     * @param goodsMarketRequestDTO
     * @return
     */
    Response<GoodsMarketResponseDTO> queryGroupBuyMarketConfig(GoodsMarketRequestDTO goodsMarketRequestDTO);
}
