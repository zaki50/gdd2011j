
package org.zakky.gdd2011.slidepuzzle;

public class SolvingState {
    private final Puzzle target_;

    private final int searchedDepth_;

    private final int[][] distanceTable_;

    public SolvingState(Puzzle target, int[][] distanceTable, int searchedDepth) {
        super();
        target_ = target;
        distanceTable_ = distanceTable;
        searchedDepth_ = searchedDepth;
    }

    public Puzzle getTarget() {
        return target_;
    }

    public int getSearchedDepth() {
        return searchedDepth_;
    }

    public int[][] getDistanceTable() {
        return distanceTable_;
    }
}
