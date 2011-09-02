
package org.zakky.gdd2011.slidepuzzle;

import java.util.List;

public class SolvingState {
    private final Puzzle target_;

    private final int searchedDepth_;

    private final List<List<Integer>> distanceTable_;

    public SolvingState(Puzzle target, List<List<Integer>> distanceTable, int searchedDepth) {
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

    public List<List<Integer>> getDistanceTable_() {
        return distanceTable_;
    }
}
