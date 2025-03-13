package group.buy.market.domain.activity.service.discount;

import group.buy.market.domain.activity.model.valobj.DiscountTypeEnum;
import group.buy.market.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

/**
 * 折扣计算抽象类
 */
public abstract class AbstractDiscountCalculateService implements DiscountCalculateService{

    /**
     * 人群标签过滤
     * @param userId 用户ID
     * @param originalPrice 商品原始价格
     * @param groupBuyDiscount 折扣配置
     * @return
     */
    @Override
    public BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        // 折扣是否需要人群标签限定参加
        if (DiscountTypeEnum.TAG.equals(groupBuyDiscount.getDiscountType())) {
            // 用户是否在标签范围内
            boolean isCrowRange = filterTagId(userId, groupBuyDiscount.getTagId());
            // 不在 - 原价
            if (!isCrowRange) {
                return originalPrice;
            }
        }
        // 折扣计算
        return doCalculate(originalPrice, groupBuyDiscount);
    }

    /**
     * 判断用户是否在标签范围内
     * @param userId
     * @param tagId
     * @return
     */
    private boolean filterTagId(String userId, String tagId) {
        // 未作实现，默认为True
        return true;
    }

    /**
     * 计算折算后价格
     * 抽象方法 - 不同策略类都继承这个方法做实现
     * @param originalPrice
     * @param groupBuyDiscount
     * @return
     */
    protected abstract BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);

}
