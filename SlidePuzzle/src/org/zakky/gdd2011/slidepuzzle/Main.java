
package org.zakky.gdd2011.slidepuzzle;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;
import org.zakky.gdd2011.slidepuzzle.solver.BreadthFirstOrderSearchSolver;
import org.zakky.gdd2011.slidepuzzle.solver.IddfsSolver;

public class Main {

    private static int leftUsed__ = 0;

    private static int rightUsed__ = 0;

    private static int upUsed__ = 0;

    private static int downUsed__ = 0;

    final public static void main(String[] args) throws Exception {
        final String input = (args.length == 0) ? "./input.txt" : args[0];
        final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(
                input), "iso8859-1"));
        try {
            final String[] limits = reader.readLine().split(" ");
            final int leftLimit = Integer.parseInt(limits[0]);
            final int rightLimit = Integer.parseInt(limits[1]);
            final int upLimit = Integer.parseInt(limits[2]);
            final int downLimit = Integer.parseInt(limits[3]);
            final int lineCount = Integer.parseInt(reader.readLine());

            for (int i = 0; i < lineCount; i++) {
                final String line = reader.readLine();
                final Puzzle puzzle = new Puzzle(line);

//                if (puzzle.getWidth() <= 4 || puzzle.getHeight() <= 4) {
//                    if (5 <= puzzle.getWidth() || 5 <= puzzle.getHeight()) {
//                        System.out.println(i + ":");
//                        continue;
//                    }

                    final IddfsSolver solver = new IddfsSolver(puzzle, 
                            leftLimit - leftUsed__, rightLimit - rightUsed__, upLimit - upUsed__,
                            downLimit - downUsed__);
                    final String answer = solver.solve();
                    if (answer == null) {
                        System.out.println(i + ":");
                    } else {
                        incrementUsedCount(answer);
                        System.out.println(i + ":" + answer);
                    }
/*
                    final SlidePuzzleSolver solver = new BreadthFirstOrderSearchSolver(puzzle,
                            leftLimit - leftUsed__, rightLimit - rightUsed__, upLimit - upUsed__,
                            downLimit - downUsed__);
                    final String answer = solver.solve();
                    if (answer == null) {
                        System.out.println(i + ":");
                    } else {
                        incrementUsedCount(answer);
                        System.out.println(i + ":" + answer);
                    }
                    */
//                } else {
//                    // まだ規模が大きくて解けない
//                    System.out.println(i + ":");
//                }
            }
        } finally {
            reader.close();
        }
    }

    private static void incrementUsedCount(String answer) {
        final char[] array = answer.toCharArray();
        for (int i = 0; i < array.length; i++) {
            final char letter = array[i];
            if (letter == Direction.UP.getLetter()) {
                upUsed__++;
            } else if (letter == Direction.DOWN.getLetter()) {
                downUsed__++;
            } else if (letter == Direction.LEFT.getLetter()) {
                leftUsed__++;
            } else if (letter == Direction.DOWN.getLetter()) {
                rightUsed__++;
            }
        }
    }
}
