
package org.zakky.gdd2011.slidepuzzle.solver;

import org.zakky.gdd2011.slidepuzzle.Puzzle;
import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;
import org.zakky.gdd2011.slidepuzzle.SlidePuzzleSolver;

public final class IddfsSolver implements SlidePuzzleSolver {

    private final int stepLimit_;

    private final Puzzle initial_;

    private final int[][] distanceTable_;

    private final char[] goal_;

    public IddfsSolver(Puzzle puzzle, int[][] distanceTable, int stepLimit) {
        this(puzzle, distanceTable, stepLimit, null);
    }

    public IddfsSolver(Puzzle puzzle, int[][] distanceTable, int stepLimit, char[] goal) {
        super();
        initial_ = puzzle;
        stepLimit_ = stepLimit;
        distanceTable_ = distanceTable;
        goal_ = goal;
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

        final Puzzle[] nextPuzzles = new Puzzle[4];
        final int[] nextDsums = new int[4];
        for (Direction dir : Direction.valuesByRandomOrder()) {
            if (p.isBackword(dir)) {
                continue;
            }
            final Puzzle next = p.move(dir);
            if (next == null) {
                continue;
            }
            if (isSolved(next)) {
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

            if (dSum < distanceSum) {
                // 減る方向
                for (int i = 0; i < nextPuzzles.length; i++) {
                    if (nextPuzzles[i] == null) {
                        nextPuzzles[i] = next;
                        nextDsums[i] = dSum;
                        break;
                    }
                }
            } else {
                for (int i = nextPuzzles.length - 1; 0 <= i; i--) {
                    if (nextPuzzles[i] == null) {
                        nextPuzzles[i] = next;
                        nextDsums[i] = dSum;
                        break;
                    }
                }
            }
        }
        for (int i = 0; i < nextPuzzles.length; i++) {
            final Puzzle next = nextPuzzles[i];
            if (next == null) {
                continue;
            }
            final String result = search(next, stepLimit, nextDsums[i]);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private boolean isSolved(Puzzle p) {
        final boolean solved;
        if (goal_ == null) {
            solved = p.isCleared();
        } else {
            solved = p.matches(goal_);
        }
        return solved;
    }
}
