package book2.ch8.puzzle;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author by darcy
 * Date on 17-6-11 下午3:56.
 * Description:
 */
public class PuzzleSolver<P, M> extends ConcurrentPuzzleSolver<P, M> {
    private final AtomicInteger taskCount = new AtomicInteger(0);

    public PuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec, ConcurrentMap<P, Boolean> seen) {
        super(puzzle, exec, seen);
    }

    @Override
    protected Runnable newTask(P p, M m, Node<P, M> pmNode) {
        return new CountingSovleTask(p, m, pmNode);
    }

    class CountingSovleTask extends SolveTask {
        public CountingSovleTask(P pos, M moves, Node<P, M> prev) {
            super(pos, moves, prev);
            taskCount.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                super.run();
            } finally {
                if (taskCount.decrementAndGet() == 0) {
                    solution.setValue(null);
                }
            }
        }
    }

}
