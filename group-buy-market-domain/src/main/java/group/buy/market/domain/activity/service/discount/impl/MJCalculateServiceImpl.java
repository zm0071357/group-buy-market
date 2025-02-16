package group.buy.market.domain.activity.service.discount.impl;

import group.buy.market.domain.activity.model.valobj.GroupBuyActivityDiscountVO;
import group.buy.market.domain.activity.service.discount.AbstractDiscountCalculateService;
import group.buy.market.types.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 满减价格计算
 */
@Slf4j
@Service("MJ")
public class MJCalculateServiceImpl extends AbstractDiscountCalculateService {

    @Override
    protected BigDecimal doCalculate(BigDecimal originalPrice, GroupBuyActivityDiscountVO.GroupBuyDiscount groupBuyDiscount) {
        String marketExpr = groupBuyDiscount.getMarketExpr();
        String[] split = marketExpr.split(Constants.SPLIT);
        // 满减需要的价格
        BigDecimal x = new BigDecimal(split[0]);
        // 抵扣的价格
        BigDecimal y = new BigDecimal(split[1]);
        // 不满足满减条件
        if (originalPrice.compareTo(x) < 0) {
            return originalPrice;
        }
        BigDecimal deductPrice = originalPrice.subtract(y);
        // 最低价格不能低于0.01元
        if (deductPrice.compareTo(BigDecimal.ZERO) <= 0) {
            return new BigDecimal("0.01");
        }
        return deductPrice;
    }
}
