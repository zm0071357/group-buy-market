package group.buy.market.domain.activity.service.discount.impl;

import group.buy.market.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import group.buy.market.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 折扣价格计算
 */
@Slf4j
@Service("ZK")
public class ZKCalculateServiceImpl extends AbstractDiscountCalculateService {

    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        String marketExpr = groupBuyDiscount.getMarketExpr();
        // 折扣百分比
        BigDecimal discount = new BigDecimal(marketExpr);
        BigDecimal deductPrice = originalPrice.multiply(discount);
        if (deductPrice.compareTo(originalPrice) <= 0) {
            return new BigDecimal("0.01");
        }
        return deductPrice;
    }
}
