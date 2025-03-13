package group.buy.market.api.dto;

import lombok.Data;

/**
 * 用户锁单请求对象
 */
@Data
public class LockMarketPayOrderRequestDTO {

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 拼单组队ID - 为空则创建新组队ID
     */
    private String teamId;

    /**
     * 活动ID
     */
    private Long activityId;

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
     * 外部交易单号
     */
    private String outTradeNo;

}

