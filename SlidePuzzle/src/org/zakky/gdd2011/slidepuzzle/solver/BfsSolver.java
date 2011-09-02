
package org.zakky.gdd2011.slidepuzzle.solver;

import java.util.Deque;
import java.util.LinkedList;

import org.zakky.gdd2011.slidepuzzle.Puzzle;
import org.zakky.gdd2011.slidepuzzle.SlidePuzzleSolver;
import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;

public class BfsSolver implements SlidePuzzleSolver {

    private final Puzzle initial_;

    public BfsSolver(Puzzle puzzle) {
        super();
        initial_ = puzzle;
    }

    @Override
    public String solve() {
        final Deque<Puzzle> queue = new LinkedList<Puzzle>();

        // 最初の一手
        for (Direction dir : Direction.valuesByRandomOrder()) {
            final Puzzle next = initial_.move(dir);
            if (next != null) {
                if (next.isCleared()) {
                    return next.getHistory();
                }
                queue.addLast(next);
            }
        }

        while (!queue.isEmpty()) {
            final Puzzle puzzle = queue.removeFirst();
            for (Direction dir : Direction.valuesByRandomOrder()) {
                if (puzzle.isBackword(dir)) {
                    continue;
                }
                //System.err.println(puzzle.getHistory());
                final Puzzle next = puzzle.move(dir);
                if (next != null) {
                    if (next.isCleared()) {
                        return next.getHistory();
                    }
                    queue.addLast(next);
                }
            }
        }
        return null;
    }
}
