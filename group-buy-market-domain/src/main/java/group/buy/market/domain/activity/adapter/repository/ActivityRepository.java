package group.buy.market.domain.activity.adapter.repository;

import group.buy.market.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import group.buy.market.domain.activity.model.valobj.SkuVO;

/**
 * 活动仓储
 * 在 domain 层提供接口，交给 infrastructure 层实现，防腐
 */
public interface ActivityRepository {

    /**
     * 查询拼团活动及拼团配置
     * @param source
     * @param channel
     * @return
     */
    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(String source, String channel);

    /**
     * 查询商品
     * @param goodsId
     * @return
     */
    SkuVO querySkuByGoodsId(String goodsId);
}
