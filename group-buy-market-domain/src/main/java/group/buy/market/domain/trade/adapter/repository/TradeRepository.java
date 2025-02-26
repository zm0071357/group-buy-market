package group.buy.market.domain.trade.adapter.repository;

import group.buy.market.domain.trade.model.aggregate.GroupBuyOrderAggregate;
import group.buy.market.domain.trade.model.entity.GroupBuyActivityEntity;
import group.buy.market.domain.trade.model.entity.MarketPayOrderEntity;
import group.buy.market.domain.trade.model.valobj.GroupBuyProgressVO;

/**
 * 拼团交易仓储
 */
public interface TradeRepository {

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
     * @param groupBuyOrderAggregate 拼团聚合对象
     * @return
     */
    MarketPayOrderEntity lockMarketPayOrder(GroupBuyOrderAggregate groupBuyOrderAggregate);

    /**
     * 查询活动信息
     * @param activityId 活动ID
     * @return
     */
    GroupBuyActivityEntity queryGroupBuyActivityByActivityId(Long activityId);

    /**
     * 查询用户参加拼团活动次数
     * @param activityId 活动ID
     * @param userId 用户ID
     */
    Integer queryOrderCount(Long activityId, String userId);
}
