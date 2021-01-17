package GameTests;

import Enums.GameState;
import classes.Game;
import classes.User;
import org.junit.Assert;
import org.junit.Test;


public class RegisterTests {
    @Test
    public void testAddPlayerSingle(){
        Game game = new Game();
        User user1 = new User(1,"Jan","Test123");
        game.addPlayer(user1);
        Assert.assertEquals(user1, game.getUser1());
    }

    @Test
    public void testAddPlayerDouble(){
        Game game = new Game();
        User user1 = new User(1,"Jan","Test123");
        User user2 = new User(2,"Piet","Test123");
        game.addPlayer(user1);
        game.addPlayer(user2);
        Assert.assertEquals(user1, game.getUser1());
        Assert.assertNotSame(user2, game.getUser1());
        Assert.assertEquals(user2, game.getUser2());
        Assert.assertNotSame(user1, game.getUser2());
    }

    @Test
    public void testGameStateChange(){
        Game game = new Game();
        User user1 = new User(1,"Jan","Test123");
        User user2 = new User(2,"Piet","Test123");
        game.addPlayer(user1);
        Assert.assertEquals(GameState.Waiting, game.getGameState());
        game.addPlayer(user2);
        Assert.assertEquals(GameState.Ready, game.getGameState());
    }
}
