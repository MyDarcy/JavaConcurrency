package book2.ch8.puzzle;

import java.util.LinkedList;
import java.util.List;

/**
 * Author by darcy
 * Date on 17-6-11 下午3:18.
 * Description:
 */
public class Node<P, M> {
    final P pos;
    final M move;
    final Node<P, M> prev;

    public Node(P pos, M moves, Node<P, M> prev) {
        this.pos = pos;
        this.move = moves;
        this.prev = prev;
    }

    List<M> asMoveList() {
        List<M> solution = new LinkedList<>();
        for(Node<P, M> n = this; n.move != null; n = n.prev) {
            solution.add(0, n.move);
        }
        return solution;
    }
}
