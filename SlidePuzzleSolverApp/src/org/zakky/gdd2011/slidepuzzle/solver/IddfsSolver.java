
package org.zakky.gdd2011.slidepuzzle.solver;

import org.zakky.gdd2011.slidepuzzle.Puzzle;
import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;
import org.zakky.gdd2011.slidepuzzle.SlidePuzzleSolver;

public final class IddfsSolver implements SlidePuzzleSolver {

    private final int stepLimit_;

    private final Puzzle initial_;

    private final int[][] distanceTable_;

    public IddfsSolver(Puzzle puzzle, int[][] distanceTable, int stepLimit) {
        super();
        initial_ = puzzle;
        stepLimit_ = stepLimit;
        distanceTable_ = distanceTable;
    }

    @Override
    public String getName() {
        return "iddfs-solver";
    }

    @Override
    public String solve() {
        final int mdZero = SolverUtil.calcManhattanDistance(initial_.getWidth() - 1,
                initial_.getHeight() - 1, initial_.getZeroX(), initial_.getZeroY());
        if ((mdZero % 2) != (stepLimit_ % 2)) {
            // 偶奇が一致しない場合は、次に頑張る
            return null;
        }

        final int distanceSum = SolverUtil.calcDistanceSum(initial_, distanceTable_);
        final String result = search(initial_, stepLimit_, distanceSum);
        return result;
    }

    private String search(Puzzle p, int stepLimit, int distanceSum) {
        if (stepLimit == 0) {
            return null;
        }

        final int zeroIndex = p.getZeroIndex();

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

            final char movedChar = next.getAt(zeroIndex);
            final int goalIndex = SolverUtil.toGoalIndex(movedChar);
            final int oldDistance = SolverUtil.calcDistance(distanceTable_, goalIndex,
                    next.getZeroIndex());
            final int newDistance = SolverUtil.calcDistance(distanceTable_, goalIndex, zeroIndex);

            final int dSum = distanceSum + (newDistance - oldDistance);
            if (stepLimit < dSum) {
                continue;
            }

            final String result = search(next, stepLimit, dSum);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
