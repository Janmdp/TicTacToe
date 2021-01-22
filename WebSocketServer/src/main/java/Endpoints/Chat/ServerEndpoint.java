package Endpoints.Chat;

import Decoders.MessageDecoder;
import Encoders.MessageEncoder;
import static Enums.CommandType.*;

import Enums.SquareState;
import classes.*;

import static Enums.GameState.*;

import javax.websocket.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@javax.websocket.server.ServerEndpoint(value = "/chat",
        decoders = {MessageDecoder.class},
        encoders = {MessageEncoder.class})
public class ServerEndpoint {

    private Session session;

    private static IGameInterface game = new Game();

    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException {
        // Get session and WebSocket connection
        System.out.println("3");
        Message message = new Message();
        message.setFrom("Server");
        message.setContent("Connected to the endpoint!!");
        message.setCommandType(Register);
        session.getAsyncRemote().sendObject(message);
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException {
        // Handle new messages
        //message.setFrom(users.get(session.getId()));
        handleCommand(message, session);
        //broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        // WebSocket connection closes
        Message message = new Message();
        message.setFrom(session.getId());
        message.setContent("Disconnected!");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private static void broadcast(Message message) throws IOException, EncodeException {
        //serverEndpoints.forEach(endpoint -> {
            //synchronized (endpoint) {
                //try {
                    //endpoint.session.getBasicRemote().
                           // sendObject(message);
                //} catch (IOException | EncodeException e) {
                   // e.printStackTrace();
                //}
            //}
        //});
    }

    private void handleCommand(Message msg, Session session){
        switch(msg.getCommandType()){
            case Register:
                //code here
                if(msg.isSingleplayer()){
                    game = new AIGame();
                }
                msg.getUser().setSession(session);
                game.addPlayer(msg.getUser());
                Message registered = new Message();
                registered.setFrom("Server");
                registered.setCommandType(Register);
                registered.setContent("Waiting for other player");
                registered.setUser(msg.getUser());
                msg.getUser().getSession().getAsyncRemote().sendObject(registered);
                if(game.getGameState() == Ready){
                    Message toReturn = game.startGame();
                    toReturn.getUser().getSession().getAsyncRemote().sendObject(toReturn);
                }
                break;

            case Turn:
                //code here
                List<Message> messages = game.updateBoard(msg);
                for (Message message:messages) {
                    message.getUser().getSession().getAsyncRemote().sendObject(message);
                }
                if(game.checkGameOver()){
                    List<Message> msgs = game.sendGameOver();
                    for (Message m: msgs){
                        m.getUser().getSession().getAsyncRemote().sendObject(m);
                    }
                }
                break;
        }
    }
}

