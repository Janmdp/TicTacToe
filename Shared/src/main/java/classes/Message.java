package classes;

import Enums.CommandType;
import Enums.SquareState;

public class Message {
    private String from;
    private CommandType commandType;
    private User user;
    private String content;
    private int location;
    private SquareState squareState;

    //standard constructors, getters, setters
    public Message() {

    }

    public Message(String from, CommandType cType, User user, String content, int location, SquareState squareState) {
        this.from = from;
        this.commandType = cType;
        this.user = user;
        this.content = content;
        this.location = location;
        this.squareState = squareState;
    }

    //methods
    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public CommandType getCommandType() { return commandType; }
    public void setCommandType(CommandType commandType) { this.commandType = commandType; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public int getLocation() { return location; }
    public void setLocation(int location) { this.location = location; }

    public SquareState getSquareState() { return squareState; }
    public void setSquareState(SquareState squareState) { this.squareState = squareState; }

    @Override
    public String toString() {
        return "Message{" +
                "commandType=" + commandType +
                ", user=" + user +
                ", msg='" + content + '\'' +
                ", location=" + location +
                ", squareState=" + squareState +
                '}';
    }
}
