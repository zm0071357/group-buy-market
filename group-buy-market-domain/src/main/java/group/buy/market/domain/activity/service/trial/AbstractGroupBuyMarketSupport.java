package group.buy.market.domain.activity.service.trial;

import group.buy.market.domain.activity.adapter.repository.ActivityRepository;
import group.buy.market.types.design.framework.tree.AbstractMultiThreadStrategyRouter;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


/**
 * 营销商品-策略路由
 * 异步加载数据
 * @param <MarketProductEntity>
 * @param <DynamicContext>
 * @param <TrialBalanceEntity>
 */
public abstract class AbstractGroupBuyMarketSupport<MarketProductEntity, DynamicContext, TrialBalanceEntity>
        extends AbstractMultiThreadStrategyRouter<MarketProductEntity, DynamicContext, TrialBalanceEntity> {

    // 超时时间
    protected long timeout = 500;

    // 活动仓储定义在抽象类中
    // 方便每个继承的节点都能直接使用该仓储，不需要重复引入
    @Resource
    protected ActivityRepository activityRepository;

    @Override
    protected void multiThread(MarketProductEntity requestParameter, DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        // 不做实现，让继承的子类做对应实现
    }
}
