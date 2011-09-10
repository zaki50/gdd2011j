
package org.zakky.gdd2011.slidepuzzle;

import java.io.IOException;
import java.io.PushbackInputStream;

import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;

public class GameMain {

    private static Puzzle initial__ = null;

    private static Puzzle current__ = null;

    public static void main(String[] args) throws Exception {
        final PushbackInputStream in = new PushbackInputStream(System.in, 1);

        int c;
        while (0 <= (c = in.read())) {
            final char ch = (char) c;
            switch (c) {
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
                    final String puzzleStr = readLine(in).trim();
                    load(puzzleStr);
                    break;
                }
                case 'c':
                case 'C':
                    reset();
                    break;
                default: {
                    final String line = readLine(in);
                    System.err.println("unknown command: " + (ch + line));
                    break;
                }
            }
        }
    }

    private static String readLine(PushbackInputStream in) throws IOException {
        final StringBuilder sb = new StringBuilder();

        int c;
        for (c = in.read(); 0 <= c && c != '\r' && c != '\n'; c = in.read()) {
            sb.append((char) c);
        }
        if (0 <= c && 0 < in.available()) {
            final int extra = in.read();
            if (0 <= extra && extra != '\r' && extra != '\n') {
                in.unread(extra);
            }
        }
        return sb.toString();
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
