package book2.ch8.puzzle;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * Author by darcy
 * Date on 17-6-11 下午3:33.
 * Description:
 */
public class ConcurrentPuzzleSolver<P, M> {
    private final Puzzle<P, M> puzzle;
    private final ExecutorService exec;
    private final ConcurrentMap<P, Boolean> seen;
    final ValueLatch<Node<P, M>> solution = new ValueLatch<>();

    public ConcurrentPuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec, ConcurrentMap<P, Boolean> seen) {
        this.puzzle = puzzle;
        this.exec = exec;
        this.seen = seen;
    }

    public List<M> sovle() throws InterruptedException {
        try {
            P p = puzzle.initialPosition();
            exec.execute(newTask(p, null, null));
            // 阻塞, 直到值被设置...
            Node<P, M> node = solution.getValue();
            return (node == null) ? null : node.asMoveList();
        } finally {
            exec.shutdown();
        }
    }

    protected Runnable newTask(P p, M m, Node<P, M> pmNode) {
        return new SolveTask(p, m, pmNode);
    }

    class SolveTask extends Node<P, M> implements Runnable {

        public SolveTask(P pos, M moves, Node<P, M> prev) {
            super(pos, moves, prev);
        }

        @Override
        public void run() {
            // 每个任务首先检查闭锁,找到就结束...
            if (solution.isSet() || seen.putIfAbsent(pos, true) != null) {
                return;
            }

            if (puzzle.isGoal(pos)) {
                solution.setValue(this);
            } else {
                for (M m : puzzle.legalMoves(pos)) {
                    exec.execute(newTask(puzzle.move(pos, m), m, this));
                }
            }
        }
    }


    class ValueLatch<T> {
        private T value;
        private final CountDownLatch done = new CountDownLatch(1);
        public boolean isSet() {
            return done.getCount() == 0;
        }

        public synchronized void setValue(T newValue) {
            if (!isSet()) {
                value = newValue;
                done.countDown();
            }
        }

        public T getValue() throws InterruptedException {
            done.await();
            synchronized (this) {
                return value;
            }
        }
    }

}
