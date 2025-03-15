package group.buy.market.types.design.framework.link.model2;

import group.buy.market.types.design.framework.link.model2.chain.BusinessLinkedList;
import group.buy.market.types.design.framework.link.model2.handler.LogicHandler;

/**
 * 责任链装配工厂
 * @param <T>
 * @param <D>
 * @param <R>
 */
public class LinkArmory<T, D, R> {

    private final BusinessLinkedList<T, D, R> logicLink;

    /**
     * 添加节点
     * @param linkName
     * @param logicHandlers
     */
    @SafeVarargs // 标记此方法使用可变参数（LogicHandler...）时是类型安全的，避免编译器警告
    public LinkArmory(String linkName, LogicHandler<T, D, R>... logicHandlers) {
        logicLink = new BusinessLinkedList<>(linkName);
        for (LogicHandler<T, D, R> logicHandler: logicHandlers){
            logicLink.add(logicHandler);
        }
    }

    /**
     * 获取责任链
     * @return
     */
    public BusinessLinkedList<T, D, R> getLogicLink() {
        return logicLink;
    }

}
