
package org.zakky.gdd2011.slidepuzzle.solver;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.zakky.gdd2011.slidepuzzle.Puzzle;
import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;
import org.zakky.gdd2011.slidepuzzle.SlidePuzzleSolver;

public final class IdLimitedBfsSolver implements SlidePuzzleSolver {

    private final Puzzle initial_;

    private final int[][] distanceTable_;

    private final int stepLimit_;

    private final Set<Integer> knownState_ = new HashSet<Integer>();

    private static final int STATE_LIMIT = 1000 * 50;

    public IdLimitedBfsSolver(Puzzle puzzle, int[][] distanceTable, int stepLimit) {
        super();
        initial_ = puzzle;
        distanceTable_ = distanceTable;
        stepLimit_ = stepLimit;
    }

    @Override
    public String getName() {
        return "id-limited-bfs-solver";
    }

    @Override
    public String solve() {
        if (initial_.isCleared()) {
            return initial_.getHistory();
        }

        TreeSet<PuzzleState> input = new TreeSet<PuzzleState>();
        TreeSet<PuzzleState> output = new TreeSet<PuzzleState>();

        final int distance = SolverUtil.calcDistanceSum(initial_, distanceTable_);
        final int scoreSum = SolverUtil.calcDistance2Sum(initial_, distanceTable_);
        final PuzzleState initialState = new PuzzleState(initial_, distance, scoreSum);

        output.add(initialState);
        knownState_.add(initial_.getBoardHash());

        while (!output.isEmpty()) {
            final TreeSet<PuzzleState> temp = input;
            input = output;
            output = temp;

            final int consumedSteps = input.first().target_.getHistory().length();
            if (stepLimit_ <= consumedSteps) {
                return null;
            }

            boolean overflow = false;

            while (!input.isEmpty()) {
                final PuzzleState state = input.pollFirst();
                final Puzzle p = state.target_;
                final int distanceSum = state.distanceSum_;
                final int oldScoreSum = state.scoreSum_;

                // TODO 距離の2乗での足切りを行う。
                // TODO 盤面の状態を保持し、重複を排除する

                final int zeroIndex = p.getZeroIndex();

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
                    final int newDistance = SolverUtil.calcDistance(distanceTable_, goalIndex,
                            zeroIndex);

                    final int dSum = distanceSum + (newDistance - oldDistance);
                    if (stepLimit_ - next.getHistory().length() < dSum) {
                        continue;
                    }

                    if (!knownState_.add(next.getBoardHash())) {
                        //System.out.println("known state");
                        continue;
                    }

                    if (overflow || STATE_LIMIT < output.size()) {
                        overflow = true;
                        output.pollLast();
                    }

                    final int oldScoreForMoved = oldDistance * oldDistance;
                    final int newScoreForMoved = newDistance * newDistance;
                    final int newScoreSum = oldScoreSum + (newScoreForMoved - oldScoreForMoved);

                    if (!output.add(new PuzzleState(next, dSum, newScoreSum))) {
                        throw new RuntimeException("should not happen.");
                    }
                }
            }
        }
        return null;
    }

    final class PuzzleState implements Comparable<PuzzleState> {
        final int distanceSum_;

        final int scoreSum_;

        final Puzzle target_;

        public PuzzleState(Puzzle target, int distanceSum, int scoreSum) {
            super();
            target_ = target;
            distanceSum_ = distanceSum;
            scoreSum_ = scoreSum;
        }

        @Override
        public int compareTo(PuzzleState o) {
            if (o == this) {
                return 0;
            }
            if (scoreSum_ < o.scoreSum_) {
                return -1;
            }
            if (o.scoreSum_ < scoreSum_) {
                return 1;
            }

            return (target_.getBoardHash() < o.target_.getBoardHash()) ? -1 : 1;
            //throw new RuntimeException();
        }
    }
}
