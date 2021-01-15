package classes;

import Enums.GameState;
import Enums.SquareState;

import static Enums.CommandType.*;
import static Enums.GameState.*;
import static Enums.SquareState.*;

import java.util.ArrayList;
import java.util.List;

import static Enums.SquareState.*;
import static Enums.GameState.*;

public class Game {
    //fields
    private static int id = 0;
    private User user1;
    private User user2;
    private SquareState[] board;
    private GameState gameState;


    //constructors
    public Game(){this.id ++;}

    //Getters and Setters
    public User getUser1() { return user1; }
    public void setUser1(User user1) { this.user1 = user1; }

    public User getUser2() { return user2; }
    public void setUser2(User user2) { this.user2 = user2; }

    public GameState getGameState() { return gameState; }

    public void setGameState(GameState gameState) { this.gameState = gameState; }

    //methods
    public Message startGame(){
        board = new SquareState[9];
        for(int i = 0; i< board.length; i++){
            board[i] = Empty;
        }
        user1.setTurn(true);
        gameState = Started;
        Message msg = new Message();
        msg.setFrom("Server");
        msg.setCommandType(Turn);
        msg.setUser(user1);
        msg.setContent("It's your turn!");
        return msg;
    }

    public List<Message> updateBoard(Message incoming){
        List<Message> toReturn = new ArrayList<Message>();

        if(checkSquareEmpty(incoming.getLocation())){
            board[incoming.getLocation()] = toFill(incoming.getUser());
            incoming.setSquareState(toFill(incoming.getUser()));
            return turn(incoming);
        }

        return toReturn;
    }

    private Boolean checkSquareEmpty(int location){
        if(board[location] == Empty){
            return true;
        }
        else{
            return false;
        }
    }

    private SquareState toFill(User incoming){
        if(incoming.getId() == user1.getId()){
            return Cross;
        }
        else if(incoming.getId() == user2.getId()){
            return Circle;
        }
        return Empty;
    }

    public void addPlayer(User user){
        if(user1 == null){
            user1 = user;
        }
        else{
            user2 = user;
            this.gameState = Ready;
        }
    }

    public List<Message> turn(Message message){
        List<Message> toReturn = new ArrayList<Message>();
        if(user1.isTurn()){
            user1.setTurn(false);
            user2.setTurn(true);
            Message msg1 = new Message("Server",Turn,user1,"Your turn is now over!", message.getLocation(), message.getSquareState());
            Message msg2 = new Message("Server",Turn,user2,"Your turn is now over!", message.getLocation(), message.getSquareState());
            toReturn.add(msg1);
            toReturn.add(msg2);
            return toReturn;
        }
        else{
            user1.setTurn(true);
            user2.setTurn(false);
            Message msg1 = new Message("Server",Turn,user1,"Your turn is now over!", message.getLocation(), message.getSquareState());
            Message msg2 = new Message("Server",Turn,user2,"Your turn is now over!", message.getLocation(), message.getSquareState());
            toReturn.add(msg1);
            toReturn.add(msg2);
            return toReturn;
        }
    }
}
