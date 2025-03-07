package group.buy.market.types.design.framework.tree;

/**
 * 策略处理器
 */
public interface StrategyHandler<T, D, R> {

    /**
     * 默认返回值
     */
    StrategyHandler DEFAULT = (T, D) -> null;

    /**
     * 对节点进行处理
     * @param requestParameter 入参
     * @param dynamicContext 动态上下文
     * @return
     */
    R apply(T requestParameter, D dynamicContext) throws Exception;



}
