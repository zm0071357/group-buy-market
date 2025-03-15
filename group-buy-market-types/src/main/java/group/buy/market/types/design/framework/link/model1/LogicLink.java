package group.buy.market.types.design.framework.link.model1;

/**
 * 单例规则责任链接口
 * @param <T>
 * @param <D>
 * @param <R>
 */
public interface LogicLink<T, D, R> extends LogicChainArmory<T, D, R> {

    /**
     * 处理节点
     * @param requestParameter
     * @param dynamicContext
     * @return
     * @throws Exception
     */
    R apply(T requestParameter, D dynamicContext) throws Exception;
}
