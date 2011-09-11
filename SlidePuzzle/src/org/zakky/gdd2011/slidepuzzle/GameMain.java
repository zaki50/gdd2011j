
package org.zakky.gdd2011.slidepuzzle;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;

public class GameMain {

    private static Puzzle initial__ = null;

    private static Puzzle current__ = null;

    public static void main(String[] args) throws Exception {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in,
                "iso8859-1"));

        String line;
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            final char ch = (char) line.charAt(0);
            switch (ch) {
                case 'U':
                case 'k':
                    move(Direction.UP);
                    break;
                case 'D':
                case 'j':
                    move(Direction.DOWN);
                    break;
                case 'L':
                case 'h':
                    move(Direction.LEFT);
                    break;
                case 'R':
                case 'l':
                    move(Direction.RIGHT);
                    break;
                case '=': {
                    final String puzzleStr = line.substring(1).trim();
                    load(puzzleStr);
                    break;
                }
                case 'c':
                case 'C':
                    reset();
                    break;
                default: {
                    System.err.println("unknown command: " + line);
                    break;
                }
            }
        }
    }

    private static void load(String puzzleStr) {
        System.out.println();
        final Puzzle prev = current__;

        final int id = (prev == null) ? 0 : prev.getId();
        final Puzzle p = new Puzzle(id, puzzleStr);
        System.out.println(p);
        System.out.flush();
        initial__ = p;
        current__ = p;
    }

    private static void reset() {
        System.out.println();
        final Puzzle p = initial__;
        if (p == null) {
            System.err.println("puzzle not loaded.");
            return;
        }
        System.out.println(p);
        System.out.flush();
        current__ = p;
    }

    private static void move(Direction dir) {
        System.out.println();
        final Puzzle p = current__;
        if (p == null) {
            System.err.println("puzzle not loaded.");
            return;
        }
        final Puzzle next = p.move(dir);
        if (next == null) {
            System.err.println("can't move.");
            return;
        }
        System.out.println(next);
        System.out.flush();
        current__ = next;
    }
}
