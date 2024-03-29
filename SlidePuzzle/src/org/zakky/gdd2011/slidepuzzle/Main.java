
package org.zakky.gdd2011.slidepuzzle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;
import org.zakky.gdd2011.slidepuzzle.solver.LimitedStatesBfsSolver;
import org.zakky.gdd2011.slidepuzzle.solver.IddfsSolver;
import org.zakky.gdd2011.slidepuzzle.solver.SolverUtil;

public class Main {

    @SuppressWarnings("unused")
    private static int leftUsed__ = 0;

    @SuppressWarnings("unused")
    private static int rightUsed__ = 0;

    @SuppressWarnings("unused")
    private static int upUsed__ = 0;

    @SuppressWarnings("unused")
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
                    final int[][] distanceTable = SolverUtil.buildDistanceTable(puzzle);
                    final SolvingState state = new SolvingState(puzzle, distanceTable, 0);
                    queue.offer(state);
                }
            } finally {
                reader.close();
            }
        }

        final List<List<String>> knownAnswers = new ArrayList<List<String>>(questionCount);
        readAnswers(questionCount, knownAnswers);

        System.out.println(leftLimit + " " + rightLimit + " " + upLimit + " " + downLimit);
        System.out.println(questionCount);

        // 既に解が分かっているものは queue から取り除く
        final Iterator<SolvingState> it = queue.iterator();
        while (it.hasNext()) {
            final SolvingState state = it.next();
            final int questionIndex = state.getTarget().getId();
            if (questionIndex < knownAnswers.size() && !knownAnswers.get(questionIndex).isEmpty()) {
                // find shortest
                String answer = "";
                for (String candidate : knownAnswers.get(questionIndex)) {
                    if (answer.isEmpty() || candidate.length() < answer.length()) {
                        answer = candidate;
                    }
                }
                incrementUsedCount(questionIndex, answer, state.getTarget());
                it.remove();
            }
        }

        Direction.setSeed(100L);

        final Thread[] workers = new Thread[THREAD_COUNT];
        final BlockingQueue<SolvingState> unsolved = new LinkedBlockingDeque<SolvingState>(
                questionCount);
        for (int i = 0; i < workers.length; i++) {
            final int threadId = i;
            workers[i] = new Thread("solver-" + threadId) {
                @Override
                public void run() {
                    try {
                        for (SolvingState state = queue.poll(1000L, TimeUnit.MILLISECONDS); state != null; state = queue
                                .poll(1000L, TimeUnit.MILLISECONDS)) {
                            final Puzzle puzzle = state.getTarget();
                            final int[][] distanceTable = state.getDistanceTable();
                            int stepsLimit = state.getSearchedDepth() + 1;
                            final SlidePuzzleSolver solver;
                            if (stepsLimit <= 58) {
                                solver = new IddfsSolver(puzzle, distanceTable, stepsLimit);
                            } else {
                                stepsLimit = 200;
                                solver = new LimitedStatesBfsSolver(puzzle, distanceTable, stepsLimit,
                                        50 * 1000);
                            }

                            final int id = puzzle.getId();
                            Thread.currentThread().setName(
                                    solver.getName() + "-" + threadId + "-p" + id + "-d"
                                            + stepsLimit);
                            //final long begin = System.nanoTime();
                            final String answer = solver.solve();
                            //final long end = System.nanoTime();
                            if (answer == null) {
                                final SolvingState newState = new SolvingState(puzzle,
                                        distanceTable, stepsLimit);
                                if (stepsLimit < 200) {
                                    // 見つからなかったので、探索済みステップ数を更新した新しいステートをoffer
                                    queue.offer(newState);
                                } else {
                                    // 200 手まで探索してダメだったので次の作戦へ
                                    unsolved.offer(newState);
                                }
                            } else {
                                //System.out.println(TimeUnit.NANOSECONDS.toMillis(end - begin) + "ms");
                                incrementUsedCount(id, answer, puzzle);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            workers[i].start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        try {
            for (SolvingState state = unsolved.poll(1000L, TimeUnit.MILLISECONDS); state != null; state = unsolved
                    .poll(1000L, TimeUnit.MILLISECONDS)) {
                final Puzzle puzzle = state.getTarget();
                final int[][] distanceTable = state.getDistanceTable();
                int stepsLimit = state.getSearchedDepth();
                final SlidePuzzleSolver solver = new LimitedStatesBfsSolver(puzzle, distanceTable,
                        stepsLimit, 400 * 1000);
                final int id = puzzle.getId();
                Thread.currentThread().setName(solver.getName() + "-p" + id + "-d" + stepsLimit);
                //final long begin = System.nanoTime();
                String answer;
                try {
                    answer = solver.solve();
                } catch (OutOfMemoryError e) {
                    answer = null;
                }
                //final long end = System.nanoTime();
                if (answer == null) {
                    System.out.println((id + 1) + ":");
                } else {
                    //System.out.println(TimeUnit.NANOSECONDS.toMillis(end - begin) + "ms");
                    incrementUsedCount(id, answer, puzzle);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void readAnswers(final int questionCount, final List<List<String>> knownAnswers)
            throws UnsupportedEncodingException, FileNotFoundException, IOException {
        for (int i = 0; i < questionCount; i++) {
            knownAnswers.add(new ArrayList<String>());
        }

        final File[] answerFiles = getTextFiles("./answers");
        for (File input : answerFiles) {

            final BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(input), "iso8859-1"));
            try {
                final String[] limits = reader.readLine().split(" ");
                /* leftLimit = */Integer.parseInt(limits[0]);
                /* rightLimit = */Integer.parseInt(limits[1]);
                /* upLimit = */Integer.parseInt(limits[2]);
                /* downLimit = */Integer.parseInt(limits[3]);
                /* final int lineCount = */Integer.parseInt(reader.readLine());

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
    }

    private static File[] getTextFiles(String parentDir) {
        final File dir = new File(parentDir);
        if (!dir.isDirectory()) {
            return new File[0];
        }
        final File[] textFiles = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".txt");
            }
        });
        return textFiles;
    }

    private static synchronized void incrementUsedCount(int id, String answer, Puzzle puzzle) {
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
        System.out.println(found__ + "/" + (id + 1) + "(" + answer.length() + " steps):" + answer);
        //System.out.println(puzzle.toString());
    }
}
