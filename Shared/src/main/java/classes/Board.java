package classes;

import Enums.SquareState;

import java.sql.SQLInvalidAuthorizationSpecException;

public class Board {
    private final SquareState[][] board;
    private int BOARD_WIDTH = 3;
    private int BOARD_HEIGHT = 3;
    private char winningMark;
    private boolean gameOver = false;

    public char getMarkAt(int row, int column) {
        return board[row][column].getMark();
    }

    public boolean isGameOver() { return gameOver; }

    public char getWinningMark() { return winningMark; }

    public SquareState[][] getBoard() { return board; }

    public void setSquareAt(Position pos, SquareState squareState){
        board[pos.getPosX()][pos.getPosY()] = squareState;
    }

    public Board(){
        board = new SquareState[BOARD_WIDTH][BOARD_HEIGHT];
        initialiseBoard();
    }

    private void initialiseBoard(){
        for(int row = 0; row < BOARD_WIDTH; row++){
            for(int col = 0; col < BOARD_HEIGHT; col++){
                board[row][col] = SquareState.Empty;
            }
        }
    }

    public boolean placeSquare(Position pos, SquareState squareState){
        if(checkAvailable(pos)){
            board[pos.getPosX()][pos.getPosY()] = squareState;
            checkWin(pos.getPosX(), pos.getPosY());
            return true;
        }
        return false;
    }

    private boolean checkAvailable(Position pos){
        if(pos.getPosX() < 0 || pos.getPosX() >= BOARD_WIDTH || pos.getPosY() < 0 || pos.getPosY() >= BOARD_HEIGHT || board[pos.getPosX()][pos.getPosY()] != SquareState.Empty){
            return false;
        }
        return true;
    }

    private void checkWin(int row, int col) {
        int rowSum = 0;
        // Check row for winner.
        for (int c = 0; c < BOARD_WIDTH; c++) {
            rowSum += getMarkAt(row, c);
        }
        if (calcWinner(rowSum) != ' ') {
            System.out.println(winningMark + " wins on row " + row);
            return;
        }

        // Check column for winner.
        rowSum = 0;
        for (int r = 0; r < BOARD_WIDTH; r++) {
            rowSum += getMarkAt(r, col);
        }
        if (calcWinner(rowSum) != ' ') {
            System.out.println(winningMark + " wins on column " + col);
            return;
        }

        // Top-left to bottom-right diagonal.
        rowSum = 0;
        for (int i = 0; i < BOARD_WIDTH; i++) {
            rowSum += getMarkAt(i, i);
        }
        if (calcWinner(rowSum) != ' ') {
            System.out.println(winningMark + " wins on the top-left to "
                    + "bottom-right diagonal");
            return;
        }

        // Top-right to bottom-left diagonal.
        rowSum = 0;
        int indexMax = BOARD_WIDTH - 1;
        for (int i = 0; i <= indexMax; i++) {
            rowSum += getMarkAt(i, indexMax - i);
        }
        if (calcWinner(rowSum) != ' ') {
            System.out.println(winningMark + " wins on the top-right to "
                    + "bottom-left diagonal.");
            return;
        }

        if (!anyMovesAvailable()) {
            gameOver = true;
            System.out.println("Tie!");
        }
    }

    private char calcWinner(int rowSum) {
        int Xwin = 'X' * BOARD_WIDTH;
        int Owin = 'O' * BOARD_WIDTH;
        if (rowSum == Xwin) {
            gameOver = true;
            winningMark = 'X';
            return 'X';
        } else if (rowSum == Owin) {
            gameOver = true;
            winningMark = 'O';
            return 'O';
        }
        return ' ';
    }

    public boolean anyMovesAvailable() {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col] == SquareState.Empty) {
                    return true;
                }
            }
        }
        return false;
    }
}
