package book2.ch8.puzzle;

import java.util.Set;

/**
 * Author by darcy
 * Date on 17-6-11 下午3:16.
 * Description:
 */

/**
 *
 * @param <P> 位置类
 * @param <M> 移动类
 */
public interface Puzzle<P, M> {
    P initialPosition();

    boolean isGoal(P position);

    Set<M> legalMoves(P position);

    P move(P position, M move);
}
