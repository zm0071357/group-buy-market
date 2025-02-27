package group.buy.market.domain.trade.model.entity;

import group.buy.market.domain.trade.model.valobj.TradeOrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 拼团预购订单营销实体对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketPayOrderEntity {

    /**
     * 拼团组队ID
     */
    private String teamId;

    /**
     * 预购订单ID
     */
    private String orderId;

    /**
     * 折扣金额
     */
    private BigDecimal deductionPrice;

    /**
     * 交易订单状态枚举
     */
    private TradeOrderStatusEnum tradeOrderStatusEnumVO;

}

