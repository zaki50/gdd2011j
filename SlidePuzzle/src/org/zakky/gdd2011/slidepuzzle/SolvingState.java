
package org.zakky.gdd2011.slidepuzzle;

public class SolvingState {
    private final Puzzle target_;

    private final int searchedDepth_;

    public SolvingState(Puzzle target, int searchedDepth) {
        super();
        target_ = target;
        searchedDepth_ = searchedDepth;
    }

    public Puzzle getTarget() {
        return target_;
    }

    public int getSearchedDepth() {
        return searchedDepth_;
    }
}
