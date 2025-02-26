package group.buy.market.types.design.framework.link.model1;

/**
 * 抽象类
 * @param <T>
 * @param <D>
 * @param <R>
 */
public abstract class AbstractLogicLink<T, D, R> implements LogicLink<T, D, R> {

    private LogicLink<T, D, R> next;

    @Override
    public LogicLink<T, D, R> next() {
        return next;
    }

    @Override
    public LogicLink<T, D, R> appendNext(LogicLink<T, D, R> next) {
        this.next = next;
        return next;
    }

    protected R next(T requestParameter, D dynamicContext) throws Exception {
        return next.apply(requestParameter, dynamicContext);
    }

}
