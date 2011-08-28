
package org.zakky.gdd2011.slidepuzzle.solver;

import java.util.Deque;
import java.util.LinkedList;

import org.zakky.gdd2011.slidepuzzle.Puzzle;
import org.zakky.gdd2011.slidepuzzle.SlidePuzzleSolver;
import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;

public class NoBoardSolver implements SlidePuzzleSolver {

    private final Puzzle initial_;

    private final int leftLimit_;

    private final int rightLimit_;

    private final int upLimit_;

    private final int downLimit_;

    public NoBoardSolver(Puzzle puzzle, int leftLimit, int rightLimit, int upLimit, int downLimit) {
        super();
        initial_ = puzzle;
        leftLimit_ = leftLimit;
        rightLimit_ = rightLimit;
        upLimit_ = upLimit;
        downLimit_ = downLimit;
    }

    @Override
    public String solve() {
        for (int x = 0; x < initial_.getWidth(); x++) {
            for (int y = 0; y < initial_.getHeight(); y++) {
                if (initial_.getAt(x, y) == '=') {
                    throw new RuntimeException("'=' is not supported.");
                }
            }
        }

        Puzzle puzzle = initial_;
        int leftUsed = 0;
        int rightUsed = 0;
        int upUsed = 0;
        int downUsed = 0;

        final StringBuilder history = new StringBuilder();
        while (true) {
            // 3x3 以下なら全探索してもっとも手数が少ないものを答えとする(数の制限を考えると最適とはかぎらないが)
            if (puzzle.getWidth() <= 3 && puzzle.getHeight() <= 3) {
                final SlidePuzzleSolver solver = new BreadthFirstOrderSearchSolver(puzzle,
                        leftLimit_ - leftUsed, rightLimit_ - rightUsed, upLimit_ - upUsed,
                        downLimit_ - downUsed);
                final String result = history.toString() + solver.solve();
                return result;
            }

            if (initial_.getWidth() < initial_.getHeight()) {
                // 縦長または正方形なので上の1行を揃える
            } else {
                // 横長なので左の1列を揃える
            }
        }
    }
}
