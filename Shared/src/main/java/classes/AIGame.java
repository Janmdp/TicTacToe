package classes;

import Enums.GameState;
import Enums.SquareState;

import java.util.ArrayList;
import java.util.List;

import static Enums.CommandType.EndGame;
import static Enums.CommandType.Turn;
import static Enums.GameState.*;
import static Enums.GameState.Ended;
import static Enums.SquareState.*;

public class AIGame implements IGameInterface {
    //fields
    private static int id = 0;
    private User user;
    private Board board;
    private GameState gameState;


    //constructors
    public AIGame(){
        this.id ++;
    }

    //Getters and Setters
    public User getUser1() { return user; }
    public void setUser1(User user1) { this.user = user; }

    public User getUser2() { return user; }
    public void setUser2(User user2) { this.user = user; }

    public GameState getGameState() { return gameState; }
    public void setGameState(GameState gameState) { this.gameState = gameState; }

    //methods
    public Message startGame(){
        board = new Board();
        gameState = Started;
        return playAI();
    }

    private Message playAI(){
        Message m = new Message();
        int[] move = MiniMax.getBestMove(board);
        Position pos = new Position(move[0], move[1]);
        if(board.placeSquare(pos, Cross)){
            Message msg = new Message();
            msg.setFrom("Server");
            msg.setCommandType(Turn);
            user.setTurn(true);
            msg.setUser(user);
            msg.setContent("AI placed piece");
            msg.setPosition(pos);
            msg.setSquareState(Cross);
            return msg;
        }
        return m;
    }

    public List<Message> updateBoard(Message incoming){
        List<Message> toReturn = new ArrayList<Message>();

        if(board.placeSquare(incoming.getPosition(), toFill(incoming.getUser()))){
            incoming.setSquareState(toFill(incoming.getUser()));
            return turn(incoming);
        }
        return toReturn;
    }

    private SquareState toFill(User incoming){
        return Circle;
    }

    public void addPlayer(User user){
        this.user = user;
        this.gameState = Ready;
    }

    public List<Message> turn(Message message){
        List<Message> toReturn = new ArrayList<Message>();
            user.setTurn(true);
            Message msg1 = new Message("Server",Turn,user,"Your turn is now over!", message.getPosition(), message.getSquareState());
            toReturn.add(msg1);
            toReturn.add(playAI());
            return toReturn;
    }

    public boolean checkGameOver() {
        if(board.isGameOver()){
            this.gameState = Ended;
            return true;
        }
        return false;
    }

    public List<Message> sendGameOver() {
        List<Message> toReturn = new ArrayList<Message>();
        if(board.getWinningMark() == 'X'){
            user.setTurn(false);

            Message msg1 = new Message();
            msg1.setFrom("server");
            msg1.setCommandType(EndGame);
            msg1.setUser(user);
            msg1.setContent("You lost!");

            toReturn.add(msg1);
            return toReturn;
        }
        else if(board.getWinningMark() == '0'){
            user.setTurn(false);

            Message msg1 = new Message();
            msg1.setFrom("server");
            msg1.setCommandType(EndGame);
            msg1.setUser(user);
            msg1.setContent("You won!");

            toReturn.add(msg1);
            return toReturn;
        }
        else{
            user.setTurn(false);

            Message msg1 = new Message();
            msg1.setFrom("server");
            msg1.setCommandType(EndGame);
            msg1.setUser(user);
            msg1.setContent("Draw!");

            toReturn.add(msg1);
            return toReturn;
        }
    }
}
