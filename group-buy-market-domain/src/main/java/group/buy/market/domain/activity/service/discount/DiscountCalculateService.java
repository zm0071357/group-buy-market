package group.buy.market.domain.activity.service.discount;

import group.buy.market.domain.activity.model.valobj.GroupBuyActivityDiscountVO;

import java.math.BigDecimal;

public interface DiscountCalculateService {

    /**
     * 折扣计算
     * @param userId 用户ID
     * @param originalPrice 商品原始价格
     * @param groupBuyDiscount 折扣配置
     * @return 商品优惠价格
     */
    BigDecimal calculate(String userId, BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount);

}
