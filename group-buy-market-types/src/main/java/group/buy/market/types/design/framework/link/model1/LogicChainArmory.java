package group.buy.market.types.design.framework.link.model1;

/**
 * 责任链装配
 * @param <T>
 * @param <D>
 * @param <R>
 */
public interface LogicChainArmory<T, D, R> {

    /**
     * 获取下一节点
     * @return
     */
    LogicLink<T, D, R> next();

    /**
     * 添加节点
     * @param next
     * @return
     */
    LogicLink<T, D, R> appendNext(LogicLink<T, D, R> next);
}
