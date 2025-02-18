package group.buy.market.infrastructure.adapter.repository;

import group.buy.market.domain.activity.adapter.repository.ActivityRepository;
import group.buy.market.domain.activity.model.valobj.DiscountTypeEnum;
import group.buy.market.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import group.buy.market.domain.activity.model.valobj.SCSkuActivityVO;
import group.buy.market.domain.activity.model.valobj.SkuVO;
import group.buy.market.infrastructure.dao.GroupBuyActivityDao;
import group.buy.market.infrastructure.dao.GroupBuyDiscountDao;
import group.buy.market.infrastructure.dao.SCSkuActivityDao;
import group.buy.market.infrastructure.dao.SkuDao;
import group.buy.market.infrastructure.dao.po.GroupBuyActivity;
import group.buy.market.infrastructure.dao.po.GroupBuyDiscount;
import group.buy.market.infrastructure.dao.po.SCSkuActivity;
import group.buy.market.infrastructure.dao.po.Sku;
import group.buy.market.infrastructure.dcc.DccServiceImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class ActivityRepositoryImpl implements ActivityRepository {

    @Resource
    private GroupBuyActivityDao groupBuyActivityDao;

    @Resource
    private GroupBuyDiscountDao groupBuyDiscountDao;

    @Resource
    private SkuDao skuDao;
    
    @Resource
    private SCSkuActivityDao scSkuActivityDao;

    @Resource
    private DccServiceImpl dccServiceImpl;

    @Override
    public GroupBuyActivityDiscountVO queryGroupBuyActivityDiscountVO(Long activityId) {
        // 查询拼团活动
        GroupBuyActivity groupBuyActivityRes = groupBuyActivityDao.queryValidGroupBuyActivityId(activityId);
        if (groupBuyActivityRes == null) {
            return null;
        }
        String discountId = groupBuyActivityRes.getDiscountId();
        // 查询拼团配置
        GroupBuyDiscount groupBuyDiscountRes = groupBuyDiscountDao.queryGroupBuyActivityDiscountByDiscountId(discountId);
        if (groupBuyDiscountRes == null) {
            return null;
        }
        GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount = GroupBuyActivityDiscountVO.GroupBuyDiscount.builder()
                .discountName(groupBuyDiscountRes.getDiscountName())
                .discountDesc(groupBuyDiscountRes.getDiscountDesc())
                .discountType(DiscountTypeEnum.get(groupBuyDiscountRes.getDiscountType()))
                .marketPlan(groupBuyDiscountRes.getMarketPlan())
                .marketExpr(groupBuyDiscountRes.getMarketExpr())
                .tagId(groupBuyDiscountRes.getTagId())
                .build();

        return GroupBuyActivityDiscountVO.builder()
                .activityId(groupBuyActivityRes.getActivityId())
                .activityName(groupBuyActivityRes.getActivityName())
                .groupBuyDiscount(groupBuyDiscount)
                .groupType(groupBuyActivityRes.getGroupType())
                .takeLimitCount(groupBuyActivityRes.getTakeLimitCount())
                .target(groupBuyActivityRes.getTarget())
                .validTime(groupBuyActivityRes.getValidTime())
                .status(groupBuyActivityRes.getStatus())
                .startTime(groupBuyActivityRes.getStartTime())
                .endTime(groupBuyActivityRes.getEndTime())
                .tagId(groupBuyActivityRes.getTagId())
                .tagScope(groupBuyActivityRes.getTagScope())
                .build();

    }

    @Override
    public SkuVO querySkuByGoodsId(String goodsId) {
        Sku sku = skuDao.querySkuByGoodsId(goodsId);
        if (sku == null) {
            return null;
        }
        return SkuVO.builder()
                .goodsId(sku.getGoodsId())
                .goodsName(sku.getGoodsName())
                .originalPrice(sku.getOriginalPrice())
                .build();

    }

    @Override
    public SCSkuActivityVO querySCSkuActivityBySCGoodsId(String source, String channel, String goodsId) {
        SCSkuActivity scSkuActivityReq = new SCSkuActivity();
        scSkuActivityReq.setChannel(channel);
        scSkuActivityReq.setSource(source);
        scSkuActivityReq.setGoodsId(goodsId);
        SCSkuActivity scSkuActivity = scSkuActivityDao.querySCSkuActivityBySCGoodsId(scSkuActivityReq);
        if (scSkuActivity == null) {
            return null;
        }
        return SCSkuActivityVO.builder()
                .source(scSkuActivity.getSource())
                .chanel(scSkuActivity.getChannel())
                .activityId(scSkuActivity.getActivityId())
                .goodsId(scSkuActivity.getGoodsId())
                .build();
    }

    @Override
    public boolean downgradeSwitch() {
        return dccServiceImpl.isDowngradeSwitch();
    }

    @Override
    public boolean cutRange(String userId) {
        return dccServiceImpl.isCutRange(userId);
    }
}
