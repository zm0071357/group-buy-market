package group.buy.market.types.design.framework.link.model2.handler;

/**
 * 责任链节点 - 定义责任链节点的处理逻辑
 * @param <T>
 * @param <D>
 * @param <R>
 */
public interface LogicHandler<T, D, R> {

    /**
     * 获取下一个节点 - 默认为空，可被子类重写以实现链传递逻辑
     * @param requestParameter
     * @param dynamicContext
     * @return
     */
    default R next(T requestParameter, D dynamicContext) {
        return null;
    }

    /**
     * 处理
     * @param requestParameter
     * @param dynamicContext
     * @return
     * @throws Exception
     */
    R apply(T requestParameter, D dynamicContext) throws Exception;

}