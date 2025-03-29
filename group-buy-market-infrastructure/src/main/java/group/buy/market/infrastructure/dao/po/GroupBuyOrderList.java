package group.buy.market.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户拼单信息
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyOrderList {

    /**
     * 自增ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 拼单组队ID
     */
    private String teamId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 活动开始时间
     */
    private Date startTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 商品ID
     */
    private String goodsId;

    /**
     * 渠道
     */
    private String source;

    /**
     * 来源
     */
    private String channel;

    /**
     * 原始价格
     */
    private BigDecimal originalPrice;

    /**
     * 折扣金额
     */
    private BigDecimal deductionPrice;

    /**
     * 支付金额
     */
    private BigDecimal payPrice;

    /**
     * 状态；0初始锁定、1消费完成
     */
    private Integer status;

    /**
     * 外部交易单号-确保外部调用唯一幂等
     */
    private String outTradeNo;

    /**
     * 外部交易时间
     */
    private Date outTradeTime;

    /**
     * 业务唯一ID
     */
    private String bizId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}

