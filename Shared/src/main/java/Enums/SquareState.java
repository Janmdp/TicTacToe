package Enums;

public enum SquareState {
    Cross('X'),
    Circle('O'),
    Empty(' ');

    private char mark;

    SquareState(char mark){
        this.mark = mark;
    }

    public char getMark() { return mark; }
}



