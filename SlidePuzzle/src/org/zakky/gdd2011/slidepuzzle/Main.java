
package org.zakky.gdd2011.slidepuzzle;

import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;
import org.zakky.gdd2011.slidepuzzle.solver.IddfsSolver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;
import org.zakky.gdd2011.slidepuzzle.solver.IddfsSolver;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Main {

    private static int leftUsed__ = 0;

    private static int rightUsed__ = 0;

    private static int upUsed__ = 0;

    private static int downUsed__ = 0;
    
    private static int found__ = 0;
    
    private static final int THREAD_COUNT = 8;

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

            Direction.setSeed(100L);

            final BlockingQueue<SolvingState> queue = new LinkedBlockingDeque<SolvingState>(lineCount);
            for (int i = 0; i < lineCount; i++) {
                final String line = reader.readLine();
                final Puzzle puzzle = new Puzzle(i, line);
                final SolvingState state = new SolvingState(puzzle, 0);
                queue.offer(state);
            }

            for (int i=0;i<THREAD_COUNT;i++) {
                final int threadId = i;
                new Thread("iddfs-solver-" + threadId) {
                    @Override
                    public void run() {
                        try {
                            for (SolvingState state = queue.poll(1000L, TimeUnit.MILLISECONDS); state != null; state = queue
                                    .poll(1000L, TimeUnit.MILLISECONDS)) {
                                final Puzzle puzzle = state.getTarget();
                                final int stepsLimit = state.getSearchedDepth() + 1;
                                final int id = puzzle.getId();
                                Thread.currentThread().setName(
                                        "iddfs-solver-" + threadId + "-p" + id + "-d" + stepsLimit);
                                final IddfsSolver solver = new IddfsSolver(puzzle, stepsLimit,
                                        leftLimit - leftUsed__, rightLimit - rightUsed__, upLimit
                                                - upUsed__, downLimit - downUsed__);
                                final String answer = solver.solve();
                                if (answer == null) {
                                    // 見つからなかったので、探索済みステップ数を更新した新しいステートをoffer
                                    final SolvingState newState = new SolvingState(puzzle, stepsLimit);
                                    queue.offer(newState);
                                } else {
                                    incrementUsedCount(answer);
                                    System.out.println(found__ + "/" + (id + 1) + "("
                                            + answer.length() + " steps):" + answer);
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        } finally {
            reader.close();
        }
    }

    private static synchronized void incrementUsedCount(String answer) {
        found__++;
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
