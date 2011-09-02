
package org.zakky.gdd2011.slidepuzzle.solver;

import org.zakky.gdd2011.slidepuzzle.Puzzle;
import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    static int calcDistanceSum(Puzzle p, List<List<Integer>> table) {
        final int width = p.getWidth();
        final int height = p.getHeight();

        int dSum = 0;
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
                final int d = calcDistance(p, table, x0, y0, x, y);
                dSum += d;
            }
        }
        return dSum;
    }

    static int calcDistance(Puzzle p, List<List<Integer>> table, int x0, int y0, int x, int y) {
        final int d = table.get(p.toIndex(x0, y0)).get(p.toIndex(x, y));
        return d;
    }

    public static List<List<Integer>> buildDistanceTable(Puzzle puzzle) {
        final List<List<Integer>> table = new ArrayList<List<Integer>>();
        for (int y = 0; y < puzzle.getHeight(); y++) {
            for (int x = 0; x < puzzle.getWidth(); x++) {
                final List<Integer> distance = calcDistance(puzzle, x, y);
                table.add(distance);
            }
        }
        return table;
    }

    static List<Integer> calcDistance(Puzzle puzzle, int x, int y) {
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

        final List<Integer> result = new ArrayList<Integer>(lengths.length);
        for (int len : lengths) {
            if (0 < len) {
                assert len == Integer.MAX_VALUE;
                result.add(null);
            } else {
                result.add(Integer.valueOf(-len));
            }
        }
        return result;
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
