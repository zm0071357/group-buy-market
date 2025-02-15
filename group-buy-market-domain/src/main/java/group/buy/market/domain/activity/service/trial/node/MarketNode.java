package group.buy.market.domain.activity.service.trial.node;

import group.buy.market.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import group.buy.market.types.design.framework.tree.StrategyHandler;

/**
 * 营销试算节点
 * @param <MarketProductEntity> 营销商品
 * @param <DynamicContext> 动态上下文
 * @param <TrialBalanceEntity> 试算结果
 */
public class MarketNode<MarketProductEntity, DynamicContext, TrialBalanceEntity> extends AbstractGroupBuyMarketSupport<MarketProductEntity, DynamicContext, TrialBalanceEntity> {
    @Override
    public TrialBalanceEntity apply(MarketProductEntity requestParameter, DynamicContext dynamicContext) throws Exception {
        return null;
    }

    @Override
    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DynamicContext dynamicContext) {
        return null;
    }
}
