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
        // 判断折扣是否包含人群标签
        if (DiscountTypeEnum.TAG.equals(groupBuyDiscount.getDiscountType())) {
            boolean isCrowRange = filterTagId(userId, groupBuyDiscount.getTagId());
            if (!isCrowRange) {
                return originalPrice;
            }
        }
        return doCalculate(originalPrice, groupBuyDiscount);
    }

    private boolean filterTagId(String userId, String tagId) {
        return true;
    }

    /**
     * 计算折算后的价格
     * @param originalPrice
     * @param groupBuyDiscount
     * @return
     */
    protected abstract BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);

}
