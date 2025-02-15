package group.buy.market.types.design.framework.tree;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 策略路由
 */
public abstract class AbstractStrategyRouter<T, D, R> implements StrategyMapper<T, D, R>, StrategyHandler<T, D, R> {

    /**
     * 默认值
     */
    @Getter
    @Setter
    protected StrategyHandler<T, D, R> defaultStrategyHandler = StrategyHandler.DEFAULT;

    /**
     * 获取并处理
     * @param requestParameter 入参
     * @param dynamicContext 动态上下文
     * @return
     * @throws Exception
     */
    public R router(T requestParameter, D dynamicContext) throws Exception {
        // 获取
        StrategyHandler<T, D, R> strategyHandler = get(requestParameter, dynamicContext);
        // 不为空 处理
        if (strategyHandler != null) {
            return strategyHandler.apply(requestParameter, dynamicContext);
        }
        // 为空 返回默认值
        return defaultStrategyHandler.apply(requestParameter, dynamicContext);
    }

}
