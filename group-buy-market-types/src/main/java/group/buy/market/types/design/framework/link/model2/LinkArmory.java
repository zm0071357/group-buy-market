package group.buy.market.types.design.framework.link.model2;

import group.buy.market.types.design.framework.link.model2.chain.BusinessLinkedList;
import group.buy.market.types.design.framework.link.model2.handler.LogicHandler;

public class LinkArmory<T, D, R> {

    private final BusinessLinkedList<T, D, R> logicLink;

    @SafeVarargs
    public LinkArmory(String linkName, LogicHandler<T, D, R>... logicHandlers) {
        logicLink = new BusinessLinkedList<>(linkName);
        for (LogicHandler<T, D, R> logicHandler: logicHandlers){
            logicLink.add(logicHandler);
        }
    }

    public BusinessLinkedList<T, D, R> getLogicLink() {
        return logicLink;
    }

}
