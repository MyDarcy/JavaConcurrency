package book2.ch8;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Author by darcy
 * Date on 17-6-11 下午3:08.
 * Description:
 *
 * 串行过程并行化...
 */
public class SequentialToParallelDemo {

    class Node<T> {
        T value;
        Node<T> left;
        Node<T> right;

        public T compute() {
            return value;
        }

        public List<Node<T>> getChildren() {
            return new ArrayList<>();
        }
    }


    public <T> void sequentialRecursive(List<Node<T>> nodes, Collection<T> results) {
        for (Node<T> node : nodes) {
            results.add(node.compute());
            sequentialRecursive(node.getChildren(), results);
        }
    }

    private static ExecutorService es = Executors.newCachedThreadPool();

    /**
     *
     * @param nodes
     * @param results
     * @param <T>
     */
    public <T> void parallelRecursive(List<Node<T>> nodes, Collection<T> results) {
        for (final Node<T> node : nodes) {
            es.execute(new Runnable() {
                @Override
                public void run() {
                    results.add(node.compute());
                }
            });
            parallelRecursive(node.getChildren(), results);
        }
    }
}
