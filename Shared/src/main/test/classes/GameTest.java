package classes;

import Enums.CommandType;
import Enums.GameState;
import Enums.GameType;
import Enums.SquareState;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        game.startGame();
        assertEquals(GameState.Started, game.getGameState());
    }

    @Test
    public void testUpdateBoard(){
        game = new Game();
        User user1 = new User(1,"Jan","Test123");
        User user2 = new User(2,"Piet","Test123");
        User user3 = new User(3,"Peter","Test123");
        game.addPlayer(user1);
        game.addPlayer(user2);
        game.startGame();

        Message msg = new Message();
        msg.setFrom("Client");
        msg.setCommandType(CommandType.Turn);
        msg.setPosition(new Position(0,0));
        msg.setSquareState(SquareState.Cross);
        msg.setUser(user1);

        Message msg2 = new Message();
        msg2.setFrom("Client");
        msg2.setCommandType(CommandType.Turn);
        msg2.setPosition(new Position(0,1));
        msg2.setSquareState(SquareState.Circle);
        msg2.setUser(user2);

        Message msg3 = new Message();
        msg3.setFrom("Client");
        msg3.setCommandType(CommandType.Turn);
        msg3.setPosition(new Position(0,1));
        msg3.setSquareState(SquareState.Circle);
        msg3.setUser(user3);

        game.updateBoard(msg);

        for(Message m: game.updateBoard(msg)){
            assertNull(m);
        }

        game.updateBoard(msg2);

        for(Message m: game.updateBoard(msg2)){
            assertNull(m);
        }

        for(Message m: game.updateBoard(msg3)){
            assertNull(m);
        }
    }

    @Test
    public void testSetGameState(){
        game = new Game();
        assertEquals(null, game.getGameState());
        game.setGameState(GameState.Ready);
        assertEquals(GameState.Ready, game.getGameState());

    }

    @Test
    public void testSetUsers(){
        game = new Game();
        User user1 = new User(1,"Jan","Test123");
        User user2 = new User(2,"Piet","Test123");
        game.setUser1(user1);
        game.setUser2(user2);

        assertEquals(user1, game.getUser1());
        assertEquals(user2, game.getUser2());
    }

    @Test
    public void testCheckGameOver(){
        game = new Game();
        User user1 = new User(1,"Jan","Test123");
        User user2 = new User(2,"Piet","Test123");
        game.addPlayer(user1);
        game.addPlayer(user2);
        game.startGame();
        assertEquals(false, game.checkGameOver());
        Message msg1 = new Message();
        msg1.setUser(user1);
        msg1.setPosition(new Position(0,0));
        Message msg2 = new Message();
        msg2.setUser(user1);
        msg2.setPosition(new Position(0,1));
        Message msg3 = new Message();
        msg3.setUser(user1);
        msg3.setPosition(new Position(0,2));
        game.updateBoard(msg1);
        game.updateBoard(msg2);
        game.updateBoard(msg3);
        assertEquals(true, game.checkGameOver());
    }

    @Test
    public void testSendGameOverXWin(){
        game = new Game();
        User user1 = new User(1,"Jan","Test123");
        User user2 = new User(2,"Piet","Test123");
        game.addPlayer(user1);
        game.addPlayer(user2);
        game.startGame();
        Message msg1 = new Message();
        msg1.setUser(user1);
        msg1.setPosition(new Position(0,0));
        Message msg2 = new Message();
        msg2.setUser(user1);
        msg2.setPosition(new Position(0,1));
        Message msg3 = new Message();
        msg3.setUser(user1);
        msg3.setPosition(new Position(0,2));
        game.updateBoard(msg1);
        game.updateBoard(msg2);
        game.updateBoard(msg3);

        int i = 0;
        for(Message msg: game.sendGameOver()){
            if(i == 0){
                assertEquals("You won!", msg.getContent());
                assertEquals(user1, msg.getUser());
            }
            else{
                assertEquals("You lost!", msg.getContent());
                assertEquals(user2, msg.getUser());
            }
            i++;
        }
    }

    @Test
    public void testSendGameOverOWin(){
        game = new Game();
        User user1 = new User(1,"Jan","Test123");
        User user2 = new User(2,"Piet","Test123");
        game.addPlayer(user1);
        game.addPlayer(user2);
        game.startGame();
        Message msg1 = new Message();
        msg1.setUser(user2);
        msg1.setPosition(new Position(0,0));
        Message msg2 = new Message();
        msg2.setUser(user2);
        msg2.setPosition(new Position(0,1));
        Message msg3 = new Message();
        msg3.setUser(user2);
        msg3.setPosition(new Position(0,2));
        game.updateBoard(msg1);
        game.updateBoard(msg2);
        game.updateBoard(msg3);

        int i = 0;
        for(Message msg: game.sendGameOver()){
            if(i == 0){
                assertEquals("You lost!", msg.getContent());
                assertEquals(user1, msg.getUser());
            }
            else{
                assertEquals("You won!", msg.getContent());
                assertEquals(user2, msg.getUser());
            }
            i++;
        }
    }

    @Test
    public void testSendGameOverDraw(){
        game = new Game();
        User user1 = new User(1,"Jan","Test123");
        User user2 = new User(2,"Piet","Test123");
        game.addPlayer(user1);
        game.addPlayer(user2);
        game.startGame();
        Message msg1 = new Message();
        msg1.setUser(user1);
        msg1.setPosition(new Position(0,0));
        Message msg2 = new Message();
        msg2.setUser(user2);
        msg2.setPosition(new Position(0,1));
        Message msg3 = new Message();
        msg3.setUser(user1);
        msg3.setPosition(new Position(0,2));
        Message msg4 = new Message();
        msg4.setUser(user2);
        msg4.setPosition(new Position(1,0));
        Message msg5 = new Message();
        msg5.setUser(user1);
        msg5.setPosition(new Position(1,1));
        Message msg6 = new Message();
        msg6.setUser(user2);
        msg6.setPosition(new Position(1,2));
        Message msg7 = new Message();
        msg7.setUser(user2);
        msg7.setPosition(new Position(2,0));
        Message msg8 = new Message();
        msg8.setUser(user1);
        msg8.setPosition(new Position(2,1));
        Message msg9 = new Message();
        msg9.setUser(user2);
        msg9.setPosition(new Position(2,2));
        game.updateBoard(msg1);
        game.updateBoard(msg2);
        game.updateBoard(msg3);
        game.updateBoard(msg4);
        game.updateBoard(msg5);
        game.updateBoard(msg6);
        game.updateBoard(msg7);
        game.updateBoard(msg8);
        game.updateBoard(msg9);

        int i = 0;
        for(Message msg: game.sendGameOver()){
            if(i == 0){
                assertEquals("Draw!", msg.getContent());
                assertEquals(user1, msg.getUser());
            }
            else{
                assertEquals("Draw!", msg.getContent());
                assertEquals(user2, msg.getUser());
            }
            i++;
        }
    }

}
