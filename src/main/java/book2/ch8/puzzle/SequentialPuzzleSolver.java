package book2.ch8.puzzle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Author by darcy
 * Date on 17-6-11 下午3:25.
 * Description:
 *
 * 串行化的解决过程...
 */
public class SequentialPuzzleSolver<P, M> {
    private final Puzzle<P, M> puzzle;
    private final Set<P> seen = new HashSet<>();

    public SequentialPuzzleSolver(Puzzle<P, M> puzzle) {
        this.puzzle = puzzle;
    }

    public List<M> solve() {
        P pos = puzzle.initialPosition();
        return search(new Node<P, M>(pos, null, null));
    }

    private List<M> search(Node<P, M> pmNode) {
        if (!seen.contains(pmNode.pos)) {
            seen.add(pmNode.pos);
            if (puzzle.isGoal(pmNode.pos)) {
                return pmNode.asMoveList();
            }
            for (M move : puzzle.legalMoves(pmNode.pos)) {
                P pos = puzzle.move(pmNode.pos, move);
                Node<P, M> child = new Node<>(pos, move, pmNode);
                List<M> result = search(child);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}
