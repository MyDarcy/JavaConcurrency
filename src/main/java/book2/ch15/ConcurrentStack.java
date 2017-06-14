package book2.ch15;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Author by darcy
 * Date on 17-6-14 下午1:29.
 * Description:
 * CAS实现非阻塞的Stack.
 */
public class ConcurrentStack<T> {
    AtomicReference<Node<T>> top = new AtomicReference<>();

    public void push(T element) {
        Node<T> newTop = new Node<>(element);
        Node<T> oldTop;
        do {
            oldTop = top.get();
            newTop.next = oldTop;
            // 此时top没有修改过,那么cas成功, 否则cas失败.失败则重试.
        } while (!top.compareAndSet(oldTop, newTop));
    }

    public Node<T> pop() {
        Node<T> oldTop;
        Node<T> newTop;
        do {
            oldTop = top.get();
            if (oldTop == null) {
                return null;
            }
            newTop = oldTop.next;
            // 同上.
        } while (!top.compareAndSet(oldTop, newTop));
        return oldTop;
    }


    static class Node<T> {
        T value;
        Node<T> next;

        public Node(T element) {
            value = element;
        }
    }


}
