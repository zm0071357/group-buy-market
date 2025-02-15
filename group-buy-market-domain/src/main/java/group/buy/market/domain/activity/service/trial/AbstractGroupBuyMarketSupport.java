package group.buy.market.domain.activity.service.trial;

import group.buy.market.types.design.framework.tree.AbstractStrategyRouter;

/**
 * 营销商品-策略路由
 * @param <MarketProductEntity>
 * @param <DynamicContext>
 * @param <TrialBalanceEntity>
 */
public abstract class AbstractGroupBuyMarketSupport<MarketProductEntity, DynamicContext, TrialBalanceEntity>
        extends AbstractStrategyRouter<MarketProductEntity, DynamicContext, TrialBalanceEntity> {
}
