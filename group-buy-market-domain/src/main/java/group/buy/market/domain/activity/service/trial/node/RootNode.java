package group.buy.market.domain.activity.service.trial.node;

import group.buy.market.domain.activity.service.trial.AbstractGroupBuyMarketSupport;
import group.buy.market.types.design.framework.tree.StrategyHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 根节点
 * @param <MarketProductEntity> 营销商品
 * @param <DynamicContext> 动态上下文
 * @param <TrialBalanceEntity> 试算结果
 */
@Slf4j
@Service
public class RootNode<MarketProductEntity, DynamicContext, TrialBalanceEntity> extends AbstractGroupBuyMarketSupport<MarketProductEntity, DynamicContext, TrialBalanceEntity> {

    /**
     * 处理
     * @param requestParameter 入参
     * @param dynamicContext 动态上下文
     * @return
     */
    @Override
    public TrialBalanceEntity apply(MarketProductEntity requestParameter, DynamicContext dynamicContext) throws Exception {
        return null;
    }

    /**
     * 获取下个节点
     * @param requestParameter 入参
     * @param dynamicContext 动态上下文
     * @return
     */
    @Override
    public StrategyHandler<MarketProductEntity, DynamicContext, TrialBalanceEntity> get(MarketProductEntity requestParameter, DynamicContext dynamicContext) {
        return null;
    }
}
