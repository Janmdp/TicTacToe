package classes;

import Enums.SquareState;

public class MiniMax {

    private static final int MAX_DEPTH = 6;

    private MiniMax() {
    }

    /**
     * Play moves on the board alternating between playing as X and O analysing
     * the board each time to return the value of the highest value move for the
     * X player. Return the highest value move when a terminal node or the
     * maximum search depth is reached.
     * @param board Board to play on and evaluate
     * @param depth The maximum depth of the game tree to search to
     * @param isMax Maximising or minimising player
     * @return Value of the board
     */
    public static int miniMax(Board board, int depth, boolean isMax) {
        int boardVal = evaluateBoard(board);

        // Terminal node (win/lose/draw) or max depth reached.
        if (Math.abs(boardVal) == 10 || depth == 0
                || !board.anyMovesAvailable()) {
            return boardVal;
        }

        // Maximising player, find the maximum attainable value.
        if (isMax) {
            int highestVal = Integer.MIN_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board.getBoard()[row][col] == SquareState.Empty) {
                        board.setSquareAt(new Position(row, col), SquareState.Cross);
                        highestVal = Math.max(highestVal, miniMax(board,
                                depth - 1, false));
                        board.setSquareAt(new Position(row, col), SquareState.Empty);
                    }
                }
            }
            return highestVal;
            // Minimising player, find the minimum attainable value;
        } else {
            int lowestVal = Integer.MAX_VALUE;
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (board.getBoard()[row][col] == SquareState.Empty) {
                        board.setSquareAt(new Position(row, col), SquareState.Circle);
                        lowestVal = Math.min(lowestVal, miniMax(board,
                                depth - 1, true));
                        board.setSquareAt(new Position(row, col), SquareState.Empty);
                    }
                }
            }
            return lowestVal;
        }
    }

    /**
     * Evaluate every legal move on the board and return the best one.
     * @param board Board to evaluate
     * @return Coordinates of best move
     */
    public static int[] getBestMove(Board board) {
        int[] bestMove = new int[]{-1, -1};
        int bestValue = Integer.MIN_VALUE;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board.getBoard()[row][col] == SquareState.Empty) {
                    board.setSquareAt(new Position(row, col), SquareState.Cross);
                    int moveValue = miniMax(board, MAX_DEPTH, false);
                    board.setSquareAt(new Position(row, col), SquareState.Empty);
                    if (moveValue > bestValue) {
                        bestMove[0] = row;
                        bestMove[1] = col;
                        bestValue = moveValue;
                    }
                }
            }
        }
        return bestMove;
    }

    /**
     * Evaluate the given board from the perspective of the X player, return
     * 10 if a winning board configuration is found, -10 for a losing one and 0
     * for a draw.
     * @param board Board to evaluate
     * @return value of the board
     */
    private static int evaluateBoard(Board board) {
        int rowSum = 0;
        int bWidth = 3;
        int Xwin = 'X' * bWidth;
        int Owin = 'O' * bWidth;

        // Check rows for winner.
        for (int row = 0; row < bWidth; row++) {
            for (int col = 0; col < bWidth; col++) {
                rowSum += board.getMarkAt(row, col);
            }
            if (rowSum == Xwin) {
                return 10;
            } else if (rowSum == Owin) {
                return -10;
            }
            rowSum = 0;
        }

        // Check columns for winner.
        rowSum = 0;
        for (int col = 0; col < bWidth; col++) {
            for (int row = 0; row < bWidth; row++) {
                rowSum += board.getMarkAt(row, col);
            }
            if (rowSum == Xwin) {
                return 10;
            } else if (rowSum == Owin) {
                return -10;
            }
            rowSum = 0;
        }

        // Check diagonals for winner.
        // Top-left to bottom-right diagonal.
        rowSum = 0;
        for (int i = 0; i < bWidth; i++) {
            rowSum += board.getMarkAt(i, i);
        }
        if (rowSum == Xwin) {
            return 10;
        } else if (rowSum == Owin) {
            return -10;
        }

        // Top-right to bottom-left diagonal.
        rowSum = 0;
        int indexMax = bWidth - 1;
        for (int i = 0; i <= indexMax; i++) {
            rowSum += board.getMarkAt(i, indexMax - i);
        }
        if (rowSum == Xwin) {
            return 10;
        } else if (rowSum == Owin) {
            return -10;
        }

        return 0;
    }

}
