package Enums;

public enum SquareState {
    Cross(2),
    Circle(1),
    Empty(0);

    private int player;

    SquareState(int player) {this.player = player;}

    public int getPlayer() { return player; }
}



