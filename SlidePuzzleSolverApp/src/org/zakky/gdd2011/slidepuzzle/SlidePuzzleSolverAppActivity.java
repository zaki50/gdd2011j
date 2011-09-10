
package org.zakky.gdd2011.slidepuzzle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.zakky.gdd2011.slidepuzzle.Puzzle.Direction;
import org.zakky.gdd2011.slidepuzzle.solver.IddfsSolver;
import org.zakky.gdd2011.slidepuzzle.solver.SolverUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

public class SlidePuzzleSolverAppActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new AsyncTask<Void, String, Void>() {

            private final ProgressDialog mProgress = new ProgressDialog(
                    SlidePuzzleSolverAppActivity.this);

            private BlockingQueue<SolvingState> mQueue;

            private int mFound = 0;

            private volatile boolean mQuit = false;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                mProgress.setTitle("reading input");
                mProgress.show();

            }

            @Override
            protected Void doInBackground(Void... params) {

                final int questionCount;
                try {
                    final InputStream is = getAssets().open("input.txt");
                    try {

                        final BufferedReader reader = new BufferedReader(new InputStreamReader(is,
                                "iso8859-1"));
                        try {
                            final String[] limits = reader.readLine().split(" ");
                            /*leftLimit =*/Integer.parseInt(limits[0]);
                            /*rightLimit =*/Integer.parseInt(limits[1]);
                            /*upLimit =*/Integer.parseInt(limits[2]);
                            /*downLimit =*/Integer.parseInt(limits[3]);
                            questionCount = Integer.parseInt(reader.readLine());

                            mQueue = new LinkedBlockingQueue<SolvingState>(questionCount);
                            for (int i = 0; i < questionCount; i++) {
                                final String line = reader.readLine();
                                final Puzzle puzzle = new Puzzle(i, line);
                                final int[][] distanceTable = SolverUtil.buildDistanceTable(puzzle);
                                final SolvingState state = new SolvingState(puzzle, distanceTable,
                                        0);
                                mQueue.offer(state);
                                if (mQuit) {
                                    return null;
                                }
                            }
                        } finally {
                            reader.close();
                        }

                    } finally {
                        try {
                            is.close();
                        } catch (IOException e) {
                            // ignore
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Direction.setSeed(100L);

                try {
                    for (SolvingState state = mQueue.poll(1000L, TimeUnit.MILLISECONDS); state != null; state = mQueue
                            .poll(1000L, TimeUnit.MILLISECONDS)) {
                        if (mQuit) {
                            return null;
                        }
                        final Puzzle puzzle = state.getTarget();
                        final int[][] distanceTable = state.getDistanceTable();
                        int stepsLimit = state.getSearchedDepth() + 1;

                        publishProgress("" + stepsLimit);

                        final SlidePuzzleSolver solver;
                        solver = new IddfsSolver(puzzle, distanceTable, stepsLimit);

                        final int id = puzzle.getId();
                        final String answer = solver.solve();
                        if (answer == null) {
                            // 見つからなかったので、探索済みステップ数を更新した新しいステートをoffer
                            final SolvingState newState = new SolvingState(puzzle, distanceTable,
                                    stepsLimit);
                            mQueue.offer(newState);
                        } else {
                            incrementUsedCount(id, answer, puzzle);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(String... values) {
                super.onProgressUpdate(values);

                if (Character.isDigit(values[0].charAt(0))) {
                    mProgress.setTitle("limit: " + values[0]);
                    //mProgress.setMessage("");
                    return;
                }
                mProgress.setMessage(values[0]);
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                mProgress.dismiss();
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                mQuit = true;
                mProgress.dismiss();
            }

            private void incrementUsedCount(int id, String answer, Puzzle puzzle) {
                mFound++;
                publishProgress("!" + mFound + "/" + (id + 1) + "(" + answer.length() + " steps):"
                        + answer);
            }
        }.execute();

    }
}
