
package org.zakky.gdd2011.slidepuzzle.solver;

import org.zakky.gdd2011.slidepuzzle.Puzzle;

public final class SolverUtil {
    static int calcManhattanDistanceSum(Puzzle p) {
        final int width = p.getWidth();
        final int height = p.getHeight();

        int mdSum = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final char c = p.getAt(x, y);
                if (c == '=') {
                    // '=' は常に正しい位置と見なせるので md は 0
                    continue;
                }
                final int value;
                if (c == '0') {
                    value = (width * height) - 1;
                } else if ('1' <= c && c <= '9') {
                    value = c - '1';
                } else if ('A' <= c && c <= 'Z') {
                    value = c - 'A' + 9;
                } else {
                    throw new RuntimeException("invalid character: " + c);
                }
                final int x0 = value % width;
                final int y0 = value / width;
                final int md = calcManhattanDistance(x0, y0, x, y);
                mdSum += md;
            }
        }
        return mdSum;
    }

    static int calcManhattanDistance(int x0, int y0, int x1, int y1) {
        final int md = Math.abs(x1 - x0) + Math.abs(y1 - y0);
        return md;
    }

    private SolverUtil() {
        throw new AssertionError("instantiation prohibited.");
    }
}
