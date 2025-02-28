package group.buy.market.domain.trade.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 拼团交易过滤反馈实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TradeLockRuleFilterBackEntity {

    /**
     * 用户参与活动的订单量（次数）
     */
    private Integer userTakeOrderCount;

}
