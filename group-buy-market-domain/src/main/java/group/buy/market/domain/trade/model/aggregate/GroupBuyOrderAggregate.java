package group.buy.market.domain.trade.model.aggregate;

import group.buy.market.domain.trade.model.entity.PayActivityEntity;
import group.buy.market.domain.trade.model.entity.PayDiscountEntity;
import group.buy.market.domain.trade.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 拼团聚合对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyOrderAggregate {

    /**
     * 用户实体对象
     */
    private UserEntity userEntity;

    /**
     * 拼团支付实体对象
     */
    private PayActivityEntity payActivityEntity;

    /**
     * 拼团支付优惠实体对象
     */
    private PayDiscountEntity payDiscountEntity;

    /**
     * 已参与拼团量
     */
    private Integer userTakeOrderCount;

}
