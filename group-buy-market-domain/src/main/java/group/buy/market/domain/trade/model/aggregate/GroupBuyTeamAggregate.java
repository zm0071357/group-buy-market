package group.buy.market.domain.trade.model.aggregate;

import group.buy.market.domain.trade.model.entity.GroupBuyTeamEntity;
import group.buy.market.domain.trade.model.entity.TradePaySuccessEntity;
import group.buy.market.domain.trade.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 拼团组队结算聚合对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupBuyTeamAggregate {

    /**
     * 用户实体对象
     */
    private UserEntity userEntity;

    /**
     * 拼团组队实体对象
     */
    private GroupBuyTeamEntity groupBuyTeamEntity;

    /**
     * 交易支付订单实体对象
     */
    private TradePaySuccessEntity tradePaySuccessEntity;

}
