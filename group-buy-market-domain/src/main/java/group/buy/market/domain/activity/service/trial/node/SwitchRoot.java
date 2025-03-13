package group.buy.market.domain.activity.service.trial.node;

import group.buy.market.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import group.buy.market.types.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 开关节点
 * 控制流程的分支或选择，决定接下来应该执行哪一条路径
 * @param <MarketProductEntity> 营销商品
 * @param <DynamicContext> 动态上下文
 * @param <TrialBalanceEntity> 试算结果
 */
@Slf4j
@Service
public class SwitchRoot<MarketProductEntity, DynamicContext, TrialBalanceEntity> extends AbstractGroupBuyMarketSupport<MarketProductEntity, DynamicContext, TrialBalanceEntity> {
    @Override
    public TrialBalanceEntity apply(MarketProductEntity requestParameter, DynamicContext dynamicContext) throws Exception {
        return null;
    }

    @Override
    protected TrialBalanceEntity doApply(MarketProductEntity requestParameter, DynamicContext dynamicContext) throws Exception {
        return null;
    }

    @Override
    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DynamicContext dynamicContext) {
        return null;
    }
}
