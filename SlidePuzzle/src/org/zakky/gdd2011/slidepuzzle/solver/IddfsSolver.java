
package org.zakky.gdd2011.slidepuzzle.solver;

import org.zakky.gdd2011.slidepuzzle.Puzzle;
import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;
import org.zakky.gdd2011.slidepuzzle.SlidePuzzleSolver;

import java.util.List;

public final class IddfsSolver implements SlidePuzzleSolver {

    private final int stepLimit_;

    private final Puzzle initial_;

    private final List<List<Integer>> distanceTable_;

    public IddfsSolver(Puzzle puzzle, List<List<Integer>> distanceTable, int stepLimit) {
        super();
        initial_ = puzzle;
        stepLimit_ = stepLimit;
        distanceTable_ = distanceTable;
    }

    @Override
    public String solve() {
        final int mdSum = getDistanceSum(initial_, distanceTable_) / 2;
        final int mdZero = getDistance(initial_, distanceTable_, initial_.getWidth() - 1,
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

            final int mdSum = getDistanceSum(next, distanceTable_);
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

    private static int getDistance(Puzzle p, List<List<Integer>> distanceTable, int x0, int y0,
            int x, int y) {
        // final int d = SolverUtil.calcDistance(p, distanceTable, x0, y0, x, y);
        final int d = SolverUtil.calcManhattanDistance(x0, y0, x, y);
        return d;
    }

    private static int getDistanceSum(Puzzle p, List<List<Integer>> distanceTable) {
        //final int dSum = SolverUtil.calcDistanceSum(p, distanceTable);
        final int dSum = SolverUtil.calcManhattanDistanceSum(p);
        return dSum;
    }
}
