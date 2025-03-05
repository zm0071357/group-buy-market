package group.buy.market.domain.activity.adapter.repository;

import group.buy.market.domain.activity.model.entity.UserGroupBuyOrderDetailEntity;
import group.buy.market.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import group.buy.market.domain.activity.model.valobj.SCSkuActivityVO;
import group.buy.market.domain.activity.model.valobj.SkuVO;
import group.buy.market.domain.activity.model.valobj.TeamStatisticVO;

import java.util.List;

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

    /**
     * 判断用户是否在人群标签范围内
     * @param userId 用户ID
     * @param tagId 标签ID
     * @return
     */
    boolean isTagCrowdRange(String userId, String tagId);

    /**
     * 查询个人拼团数据
     * @param activityId 活动ID
     * @param userId 用户ID
     * @param ownerCount 个人拼团数量
     * @return
     */
    List<UserGroupBuyOrderDetailEntity> queryInProgressUserGroupBuyOrderDetailListByOwner(Long activityId, String userId, int ownerCount);

    /**
     * 查询其他拼团数据
     * @param activityId 活动ID
     * @param userId 用户ID
     * @param randomCount 随机数量
     * @return
     */
    List<UserGroupBuyOrderDetailEntity> queryInProgressUserGroupBuyOrderDetailListByRandom(Long activityId, String userId, int randomCount);

    /**
     * 统计拼团数据
     * @param activityId
     * @return
     */
    TeamStatisticVO queryTeamStatisticByActivityId(Long activityId);
}
