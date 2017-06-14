package book2.ch15;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Author by darcy
 * Date on 17-6-14 下午2:24.
 * Description:
 */
public class LinkedQueue<T> {

    private static class Node<T> {
        final T item;
        final AtomicReference<Node<T>> next;

        public Node(T item, AtomicReference<Node<T>> next) {
            this.item = item;
            this.next = next;
        }
    }

    private final Node<T> dummy = new Node<>(null, null);
    private final AtomicReference<Node<T>> head = new AtomicReference<>(dummy);
    private final AtomicReference<Node<T>> tail = new AtomicReference<>(dummy);

    /**
     * 两个更新操作
     * １. 首先更新当前最后节点的next域使其指向新的节点(维持链表序)
     * 2. 更新tail指针使其指向新节点.（维护tail指针）
     * 但是这两个步骤的过程中可能处于中间状态.
     * @param element
     * @return
     */
    public boolean put(T element) {
        Node<T> newNode = new Node<>(element, null);
        while (true) {
            Node<T> currentTail = tail.get();
            Node tailNextNode = currentTail.next.get();
            // 此时tail尚未更新
            if (currentTail == tail.get()) {
                // 中间状态, tail后面加了新的节点但是tail还没有更新．
                if (tailNextNode != null) {
                    tail.compareAndSet(currentTail, tailNextNode);
                } else {
                    // tailNextNode为null, 处于稳定状态.插入新的节点. 此时要用现在的尾节点来做
                    // 以维护节点的状态.
                    if (currentTail.next.compareAndSet(null, newNode)) {
                        tail.compareAndSet(currentTail, newNode);
                        return true;
                    }
                }
            }
        }
    }


}
