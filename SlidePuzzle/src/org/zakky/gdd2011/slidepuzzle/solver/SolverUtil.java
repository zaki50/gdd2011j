
package org.zakky.gdd2011.slidepuzzle.solver;

import org.zakky.gdd2011.slidepuzzle.Puzzle;
import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;

import java.util.Arrays;

public final class SolverUtil {
    static int toGoalIndex(char c) {
        if ('1' <= c && c <= '9') {
            final int index = c - '1';
            return index;
        }
        if ('A' <= c && c <= 'Z') {
            final int index = c - 'A' + 9;
            return index;
        }
        throw new RuntimeException("invalid character: " + c);
    }

    static int calcManhattanDistanceSum(Puzzle p) {
        final int width = p.getWidth();
        final int height = p.getHeight();

        int mdSum = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final char c = p.getAt(x, y);
                if (c == '=') {
                    // '=' は常に正しい位置と見なせるので除外
                    continue;
                }
                if (c == '0') {
                    // '0' は結果に含めないので除外
                    continue;
                }
                if (c == '0') {
                    continue;
                }
                final int goalIndex = toGoalIndex(c);
                final int x0 = goalIndex % width;
                final int y0 = goalIndex / width;
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

    static int calcDistanceSum(Puzzle p, int[][] table) {
        final int width = p.getWidth();
        final int height = p.getHeight();
        final int length = width * height;

        int dSum = 0;
        for (int index = 0; index < length; index++) {
            final char c = p.getAt(index);
            if (c == '=') {
                // '=' は常に正しい位置と見なせるので除外
                continue;
            }
            if (c == '0') {
                // '0' は結果に含めないので除外
                continue;
            }
            final int goalIndex = toGoalIndex(c);
            final int d = calcDistance(table, goalIndex, index);
            dSum += d;
        }
        return dSum;
    }

    static int calcDistance(int[][]  table, int index0, int index) {
        final int d = table[index0][index];
        return d;
    }

    public static int[][] buildDistanceTable(Puzzle puzzle) {
        final int[][] table = new int[puzzle.getWidth() * puzzle.getHeight()][];
        for (int y = 0; y < puzzle.getHeight(); y++) {
            for (int x = 0; x < puzzle.getWidth(); x++) {
                final int[] distance = calcDistance(puzzle, x, y);
                table[puzzle.toIndex(x, y)] = distance;
            }
        }
        return table;
    }

    static int[] calcDistance(Puzzle puzzle, int x, int y) {
        // 未確定な距離は正数、確定な距離は0以下で表される要素を保持する配列
        final int[] lengths = new int[puzzle.getWidth() * puzzle.getHeight()];
        Arrays.fill(lengths, Integer.MAX_VALUE);
        int currentIndex = puzzle.toIndex(x, y);
        lengths[currentIndex] = 0;

        final Direction[] dirs = Direction.values();
        do {
            // 確定処理
            lengths[currentIndex] = -lengths[currentIndex];

            for (Direction dir : dirs) {
                final int nextIndex = puzzle.getNextIndex(currentIndex, dir);
                if (nextIndex < 0 || puzzle.getAt(nextIndex) == '=') {
                    continue;
                }
                final int currentDistance = -lengths[currentIndex];
                final int nextDistance = lengths[nextIndex];
                if (0 < nextDistance && currentDistance < nextDistance) {
                    lengths[nextIndex] = currentDistance + 1;
                }
            }
        } while (0 <= (currentIndex = find(lengths)));

        for (int i=0;i<lengths.length;i++){
            final int len = lengths[i];
            if (0 < len) {
                assert len == Integer.MAX_VALUE;
                lengths[i] = len;
            } else {
                lengths[i] = -len;
            }
        }
        return lengths;
    }

    private static int find(int[] lengths) {
        int minIndex = -1;
        for (int i = 0; i < lengths.length; i++) {
            final int len = lengths[i];
            if (len <= 0) {
                continue;
            }
            if (minIndex < 0 || len < lengths[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }

    private SolverUtil() {
        throw new AssertionError("instantiation prohibited.");
    }
}
