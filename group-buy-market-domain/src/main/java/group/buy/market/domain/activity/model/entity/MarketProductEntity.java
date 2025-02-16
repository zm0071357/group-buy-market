package group.buy.market.domain.activity.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 营销商品
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketProductEntity {

    /**
     * 用户 Id
     */
    private String userId;

    /**
     * 商品 Id
     */
    private String goodsId;

    /**
     * 来源
     */
    private String source;

    /**
     * 渠道
     */
    private String channel;

}
