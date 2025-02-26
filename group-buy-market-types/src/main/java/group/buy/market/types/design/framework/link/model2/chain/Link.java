package group.buy.market.types.design.framework.link.model2.chain;

public interface Link<E> {

    boolean add(E e);

    boolean addFirst(E e);

    boolean addLast(E e);

    boolean remove(Object o);

    E get(int index);

    void printLinkList();

}

