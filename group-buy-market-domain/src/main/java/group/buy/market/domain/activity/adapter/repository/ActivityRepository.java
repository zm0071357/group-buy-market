package group.buy.market.domain.activity.adapter.repository;

import group.buy.market.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import group.buy.market.domain.activity.model.valobj.SCSkuActivityVO;
import group.buy.market.domain.activity.model.valobj.SkuVO;

/**
 * 活动仓储
 * 在 domain 层提供接口，交给 infrastructure 层实现，防腐
 */
public interface ActivityRepository {

    /**
     * 查询拼团活动及拼团配置
     * @param activityId 拼团活动ID
     * @return
     */
    GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(Long activityId);

    /**
     * 查询商品
     * @param goodsId 商品ID
     * @return
     */
    SkuVO querySkuByGoodsId(String goodsId);


    /**
     * 查询商品活动配置关联
     * @param source 渠道
     * @param channel 来源
     * @param goodsId 商品ID
     * @return
     */
    SCSkuActivityVO querySCSkuActivityBySCGoodsId(String source, String channel, String goodsId);

    /**
     * 判断服务是否降级
     * @return
     */
    boolean downgradeSwitch();

    /**
     * 判断用户ID是否在切量范围内
     * @return
     */
    boolean cutRange(String userId);
}
