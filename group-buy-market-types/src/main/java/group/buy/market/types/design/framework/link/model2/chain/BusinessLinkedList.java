package group.buy.market.types.design.framework.link.model2.chain;

import group.buy.market.types.design.framework.link.model2.handler.LogicHandler;

/**
 * 将链表本身作为责任链的容器
 * @param <T>
 * @param <D>
 * @param <R>
 */
public class BusinessLinkedList<T, D, R> extends LinkedList<LogicHandler<T, D, R>> implements LogicHandler<T, D, R> {

    public BusinessLinkedList(String name) {
        super(name);
    }

    /**
     * 遍历链表的每个节点，依次调用其 apply 方法
     * @param requestParameter
     * @param dynamicContext
     * @return
     * @throws Exception
     */
    @Override
    public R apply(T requestParameter, D dynamicContext) throws Exception {
        Node<LogicHandler<T, D, R>> current = this.first;
        do {
            LogicHandler<T, D, R> item = current.item;
            R apply = item.apply(requestParameter, dynamicContext);
            if (null != apply) return apply;

            current = current.next;
        } while (null != current);
        return null;
    }

}

