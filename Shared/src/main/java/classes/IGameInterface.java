package classes;

import Enums.GameState;
import Enums.SquareState;

import java.util.List;

public interface IGameInterface {
    User getUser1();
    void setUser1(User user);

    User getUser2();
    void setUser2(User user);

    GameState getGameState();
    void setGameState(GameState gameState);

    Message startGame();
    List<Message> updateBoard(Message incoming);

    private SquareState toFill(User incoming) { return null; }

    void addPlayer(User user);
    List<Message> turn(Message message);
    boolean checkGameOver();
    List<Message> sendGameOver();

}
