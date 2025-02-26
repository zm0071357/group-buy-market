package group.buy.market.domain.activity.service.discount;

import group.buy.market.domain.activity.adapter.repository.ActivityRepository;
import group.buy.market.domain.activity.model.valobj.DiscountTypeEnum;
import group.buy.market.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * 折扣计算抽象类
 */
@Slf4j
public abstract class AbstractDiscountCalculateService implements DiscountCalculateService {

    @Resource
    protected ActivityRepository activityRepository;

    /**
     * 人群标签过滤
     * @param userId 用户ID
     * @param originalPrice 商品原始价格
     * @param groupBuyDiscount 折扣配置
     * @return
     */
    @Override
    public BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        // 判断折扣是否包含人群标签
        if (DiscountTypeEnum.TAG.equals(groupBuyDiscount.getDiscountType())) {
            boolean isCrowRange = filterTagId(userId, groupBuyDiscount.getTagId());
            if (!isCrowRange) {
                log.info("折扣优惠计算拦截，用户不在人群标签范围内 userId:{}", userId);
                return originalPrice;
            }
        }
        return doCalculate(originalPrice, groupBuyDiscount);
    }

    /**
     * 人群过滤
     * @param userId 用户ID
     * @param tagId 标签ID
     * @return
     */
    private boolean filterTagId(String userId, String tagId) {
        return activityRepository.isTagCrowdRange(userId, tagId);
    }

    /**
     * 计算折算后的价格
     * @param originalPrice
     * @param groupBuyDiscount
     * @return
     */
    protected abstract BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);

}
