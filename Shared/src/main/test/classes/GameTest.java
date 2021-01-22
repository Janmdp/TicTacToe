package classes;

import Enums.GameState;
import Enums.GameType;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;

public class GameTest {
    Game game = new Game();
    @Test
    public void testAddPlayerSingle(){
        game = new Game();
        User user1 = new User(1,"Jan","Test123");
        game.addPlayer(user1);
        assertEquals(user1, game.getUser1());
    }

    @Test
    public void testAddPlayerDouble(){
        game = new Game();
        User user1 = new User(1,"Jan","Test123");
        User user2 = new User(2,"Piet","Test123");
        game.addPlayer(user1);
        game.addPlayer(user2);
        assertEquals(user1, game.getUser1());
        assertNotSame(user2, game.getUser1());
        assertEquals(user2, game.getUser2());
        assertNotSame(user1, game.getUser2());
    }

    @Test
    public void testGameStateChange(){
        game = new Game();
        User user1 = new User(1,"Jan","Test123");
        User user2 = new User(2,"Piet","Test123");
        game.addPlayer(user1);
        assertEquals(GameState.Waiting, game.getGameState());
        game.addPlayer(user2);
        assertEquals(GameState.Ready, game.getGameState());
    }
}
