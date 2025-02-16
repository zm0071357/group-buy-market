package group.buy.market.domain.activity.service.discount.impl;

import group.buy.market.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import group.buy.market.domain.activity.service.discount.AbstractDiscountCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 直减价格计算
 */
@Slf4j
@Service("ZJ")
public class ZJCalculateServiceImpl extends AbstractDiscountCalculateService {

    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        String marketExpr = groupBuyDiscount.getMarketExpr();
        // 直减
        BigDecimal deduct = new BigDecimal(marketExpr);
        BigDecimal deductPrice = originalPrice.subtract(deduct);
        if (deductPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.01");
        }
        return deductPrice;
    }
}
