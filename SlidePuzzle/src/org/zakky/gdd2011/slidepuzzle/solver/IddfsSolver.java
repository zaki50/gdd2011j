
package org.zakky.gdd2011.slidepuzzle.solver;

import org.zakky.gdd2011.slidepuzzle.Puzzle;
import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;
import org.zakky.gdd2011.slidepuzzle.SlidePuzzleSolver;

public final class IddfsSolver implements SlidePuzzleSolver {

    private final int stepLimit_;

    private final Puzzle initial_;

    private final int leftLimit_;

    private final int rightLimit_;

    private final int upLimit_;

    private final int downLimit_;

    public IddfsSolver(Puzzle puzzle, int stepLimit, int leftLimit, int rightLimit, int upLimit, int downLimit) {
        super();
        initial_ = puzzle;
        stepLimit_ = stepLimit;
        leftLimit_ = leftLimit;
        rightLimit_ = rightLimit;
        upLimit_ = upLimit;
        downLimit_ = downLimit;
    }

    @Override
    public String solve() {
        final int mdSum = SolverUtil.calcManhattanDistanceSum(initial_) / 2;
        final int mdZero = SolverUtil.calcManhattanDistance(initial_.getWidth() - 1,
                initial_.getHeight() - 1, initial_.getZeroX(), initial_.getZeroY());
        final int expectedLeastSteps = mdSum + ((mdSum % 2) == (mdZero % 2) ? 0 : 1);
        if ((expectedLeastSteps % 2) != (stepLimit_ % 2)) {
            // 偶奇が一致しない場合は、次に頑張る
            return null;
        }

        final String result = search(initial_, stepLimit_);
        return result;
    }

    private String search(Puzzle p, int stepLimit) {
        if (stepLimit == 0) {
            return null;
        }

        stepLimit--;
        for (Direction dir : Direction.valuesByRandomOrder()) {
            if (p.isBackword(dir)) {
                continue;
            }
            final Puzzle next = p.move(dir);
            if (next == null) {
                continue;
            }
            if (next.isCleared()) {
                return next.getHistory();
            }
            
            final int mdSum = SolverUtil.calcManhattanDistanceSum(next);
            if (mdSum <= 4) {
                // 最後のほうは正確に
                if ((stepLimit * 2) < mdSum) {
                    continue;
                }
            } else {
                // ゴールが遠いうちは多めに狩る
                if (stepLimit < mdSum) {
                    continue;
                }
            }
            
            final String result = search(next, stepLimit);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
