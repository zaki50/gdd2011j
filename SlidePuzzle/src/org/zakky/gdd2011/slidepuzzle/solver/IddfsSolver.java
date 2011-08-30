
package org.zakky.gdd2011.slidepuzzle.solver;

import org.zakky.gdd2011.slidepuzzle.Puzzle;
import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;
import org.zakky.gdd2011.slidepuzzle.SlidePuzzleSolver;

public final class IddfsSolver implements SlidePuzzleSolver {

    private static final int STEP_LIMIT = 52;

    private final Puzzle initial_;

    private final int leftLimit_;

    private final int rightLimit_;

    private final int upLimit_;

    private final int downLimit_;

    public IddfsSolver(Puzzle puzzle, int leftLimit, int rightLimit, int upLimit, int downLimit) {
        super();
        initial_ = puzzle;
        leftLimit_ = leftLimit;
        rightLimit_ = rightLimit;
        upLimit_ = upLimit;
        downLimit_ = downLimit;
    }

    @Override
    public String solve() {
        final int expectedLeastSteps = SolverUtil.calcManhattanDistanceSum(initial_);
        for (int stepLimit = expectedLeastSteps; stepLimit <= STEP_LIMIT; stepLimit += 2) {
            final String result = search(initial_.clone(), stepLimit);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    private String search(Puzzle p, int stepLimit) {
        if (stepLimit == 0) {
            return null;
        }
        //System.err.println(p.getHistory());

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
            if (stepLimit < SolverUtil.calcManhattanDistanceSum(next)) {
                continue;
            }
            final String result = search(next, stepLimit);
            if (result != null) {
                return result;
            }
        }
        return null;
    }
}
