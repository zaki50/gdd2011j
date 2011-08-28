
package org.zakky.gdd2011.slidepuzzle;

import java.util.Random;

public class Puzzle {

    public enum Direction {
        UP('U'), DOWN('D'), LEFT('L'), RIGHT('R');
        private final char letter_;

        private static final Random RAND = new Random();

        Direction(char letter) {
            letter_ = letter;
        }

        public char getLetter() {
            return letter_;
        }

        public Direction backword() {
            switch (this) {
                case UP:
                    return DOWN;
                case DOWN:
                    return DOWN;
                case LEFT:
                    return RIGHT;
                case RIGHT:
                    return LEFT;
                default:
                    throw new RuntimeException();
            }
        }

        public static Direction[] valuesByRandomOrder() {
            final Direction[] v = values();
            final Direction[] result = new Direction[4];
            for (int i = 0; i < result.length; i++) {
                int target = RAND.nextInt(result.length - i);
                for (int j = 0; j < result.length; j++) {
                    if (v[j] != null) {
                        if (target == 0) {
                            result[i] = v[j];
                            v[j] = null;
                            break;
                        }
                        target--;
                    }
                }
            }
            return result;
        }
    }

    private final byte[] board_;

    private final int width_;

    private final int height_;

    private final int zeroIndex_;

    private final StringBuilder history_;

    public Puzzle(String line) {
        final String[] split = line.split(",");
        assert split.length == 3;
        width_ = Integer.parseInt(split[0]);
        height_ = Integer.parseInt(split[1]);
        final String panels = split[2];
        final int size = panels.length();
        board_ = new byte[size];
        int zeroIndex = -1;
        for (int i = 0; i < size; i++) {
            final char panel = panels.charAt(i);
            assert ('0' <= panel && panel <= '9') || panel == '=';
            board_[i] = (byte) panel;
            if (panel == '0') {
                zeroIndex = i;
            }
        }
        if (zeroIndex == -1) {
            throw new RuntimeException("'0' not found.");
        }
        zeroIndex_ = zeroIndex;
        history_ = new StringBuilder();
    }

    public Puzzle(int width, int height, byte[] board, int zeroIndex, int prevZeroIndex,
            String history) {
        super();
        width_ = width;
        height_ = height;
        board_ = board;
        zeroIndex_ = zeroIndex;
        history_ = new StringBuilder(history);
    }

    public int getWidth() {
        return width_;
    }

    public int getHeight() {
        return height_;
    }

    public char getAt(int x, int y) {
        if (x < 0 || width_ <= x) {
            throw new ArrayIndexOutOfBoundsException("out of bounds: x(" + x + ")");
        }
        if (y < 0 || height_ <= y) {
            throw new ArrayIndexOutOfBoundsException("out of bounds: y(" + y + ")");
        }
        final int index = toIndex(x, y);
        final byte result = board_[index];
        return (char) result;
    }

    public char getNext(int x, int y, Direction dir) {
        if (x < 0 || width_ <= x) {
            throw new ArrayIndexOutOfBoundsException("out of bounds: x(" + x + ")");
        }
        if (y < 0 || height_ <= y) {
            throw new ArrayIndexOutOfBoundsException("out of bounds: y(" + y + ")");
        }
        final int index = getNextIndex(toIndex(x, y), dir);
        if (index < 0) {
            return '=';
        }
        final byte result = board_[index];
        return (char) result;
    }

    public char getNext(Direction dir) {
        final int index = getNextIndex(zeroIndex_, dir);
        if (index < 0) {
            return '=';
        }
        final byte result = board_[index];
        return (char) result;
    }

    public boolean canMove(Direction dir) {
        final int nextIndex = getNextIndex(zeroIndex_, dir);
        return 0 <= nextIndex;
    }

    public boolean isBackword(Direction dir) {
        if (history_.length() == 0) {
            return false;
        }

        final char lastMove = history_.charAt(history_.length() - 1);
        return lastMove == dir.backword().getLetter();
    }

    public Puzzle move(Direction dir) {
        final int nextIndex = getNextIndex(zeroIndex_, dir);
        if (nextIndex < 0) {
            return null;
        }
        if (board_[nextIndex] == '=') {
            return null;
        }
        final byte[] board = board_.clone();
        board[zeroIndex_] = board[nextIndex];
        board[nextIndex] = '0';
        return new Puzzle(width_, height_, board, nextIndex, zeroIndex_, history_.toString()
                + dir.getLetter());
    }

    public boolean move2(Direction dir) {
        final int nextIndex = getNextIndex(zeroIndex_, dir);
        if (nextIndex < 0) {
            return false;
        }
        if (board_[nextIndex] == '=') {
            return false;
        }
        board_[zeroIndex_] = board_[nextIndex];
        board_[nextIndex] = '0';
        history_.append(dir.getLetter());
        return true;
    }

    private static final char[] A = {
            '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '0'
    };

    public boolean isCleared() {
        for (int i = 0; i < board_.length; i++) {
            if (board_[i] != A[i] && board_[i] != '=' && board_[i] != '0') {
                return false;
            }
        }
        return true;
    }

    public String getHistory() {
        return history_.toString();
    }

    private int toIndex(int x, int y) {
        assert 0 <= x && x < width_ && 0 <= y && y < height_;
        final int index = x + y * width_;
        return index;
    }

    private int getNextIndex(int fromIndex, Direction dir) {
        switch (dir) {
            case UP: {
                final int index = fromIndex - width_;
                if (index < 0) {
                    return -1;
                }
                return index;
            }
            case DOWN: {
                final int index = fromIndex + width_;
                if (board_.length <= index) {
                    return -1;
                }
                return index;
            }
            case LEFT: {
                if (zeroIndex_ % width_ == 0) {
                    return -1;
                }
                final int index = fromIndex - 1;
                return index;
            }
            case RIGHT: {
                final int index = fromIndex + 1;
                if (index % width_ == 0) {
                    return -1;
                }
                return index;
            }
            default:
                return -1;
        }
    }
}