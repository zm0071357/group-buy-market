package group.buy.market.domain.trade.service.lock;

import group.buy.market.domain.trade.model.entity.MarketPayOrderEntity;
import group.buy.market.domain.trade.model.entity.PayActivityEntity;
import group.buy.market.domain.trade.model.entity.PayDiscountEntity;
import group.buy.market.domain.trade.model.entity.UserEntity;
import group.buy.market.domain.trade.model.valobj.GroupBuyProgressVO;

/**
 * 拼团交易锁单
 */
public interface TradeLockOrderService {

    /**
     * 查询未支付订单
     * @param userId 用户ID
     * @param outTradeNo 订单号
     * @return
     */
    MarketPayOrderEntity queryNoPayMarketPayOrderByOutTradeNo(String userId, String outTradeNo);

    /**
     * 查询拼团进度
     * @param teamId 拼团ID
     * @return
     */
    GroupBuyProgressVO queryGroupBuyProgress(String teamId);

    /**
     * 锁单
     * @param userEntity 用户实体对象
     * @param payActivityEntity 拼团支付实体对象
     * @param payDiscountEntity 拼团支付优惠实体对象
     * @return
     */
    MarketPayOrderEntity lockMarketPayOrder(UserEntity userEntity, PayActivityEntity payActivityEntity, PayDiscountEntity payDiscountEntity) throws Exception;

}
