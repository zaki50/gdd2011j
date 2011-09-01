
package org.zakky.gdd2011.slidepuzzle;

import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;
import org.zakky.gdd2011.slidepuzzle.solver.IddfsSolver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

        final int questionCount;
        final BlockingQueue<SolvingState> queue;
        final int leftLimit;
        final int rightLimit;
        final int upLimit;
        final int downLimit;

        {
            final String input = (args.length == 0) ? "./input.txt" : args[0];
            final BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(input), "iso8859-1"));
            try {
                final String[] limits = reader.readLine().split(" ");
                leftLimit = Integer.parseInt(limits[0]);
                rightLimit = Integer.parseInt(limits[1]);
                upLimit = Integer.parseInt(limits[2]);
                downLimit = Integer.parseInt(limits[3]);
                questionCount = Integer.parseInt(reader.readLine());

                queue = new LinkedBlockingDeque<SolvingState>(questionCount);
                for (int i = 0; i < questionCount; i++) {
                    final String line = reader.readLine();
                    final Puzzle puzzle = new Puzzle(i, line);
                    final SolvingState state = new SolvingState(puzzle, 0);
                    queue.offer(state);
                }
            } finally {
                reader.close();
            }
        }

        final List<List<String>> knownAnswers = new ArrayList<List<String>>(questionCount);
        {
            final String input = (args.length < 2) ? "./answers/answer3.txt" : args[1];
            final BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(input), "iso8859-1"));
            try {
                final String[] limits = reader.readLine().split(" ");
                /* leftLimit = */Integer.parseInt(limits[0]);
                /* rightLimit = */Integer.parseInt(limits[1]);
                /* upLimit = */Integer.parseInt(limits[2]);
                /* downLimit = */Integer.parseInt(limits[3]);
                /* final int lineCount = */Integer.parseInt(reader.readLine());

                for (int i = 0; i < questionCount; i++) {
                    knownAnswers.add(new ArrayList<String>());
                }

                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    final String[] split = line.split(":");
                    if (split.length < 2) {
                        continue;
                    }
                    final String answer = split[1];
                    if (answer.trim().isEmpty()) {
                        continue;
                    }

                    // 問題番号(1-base)
                    final int questionNumber = FormatterMain.getQuestionNumber(split[0]);
                    final List<String> answers = knownAnswers.get(questionNumber - 1);
                    answers.add(answer);
                }

            } finally {
                reader.close();
            }
        }

        // 既に解が分かっているものは queue から取り除く
        final Iterator<SolvingState> it = queue.iterator();
        while (it.hasNext()) {
            final SolvingState state = it.next();
            final int questionIndex = state.getTarget().getId();
            if (!knownAnswers.get(questionIndex).isEmpty()) {
                System.err.println("removed p" + (questionIndex + 1) + " from queue.");
                it.remove();
            }
        }

        Direction.setSeed(100L);

        System.out.println(leftLimit + " " + rightLimit + " " + upLimit + " " + downLimit);

        for (int i = 0; i < THREAD_COUNT; i++) {
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
                                System.out.println(found__ + "/" + (id + 1) + "(" + answer.length()
                                        + " steps):" + answer);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
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
