package Endpoints.Chat;

import Decoders.MessageDecoder;
import Encoders.MessageEncoder;
import static Enums.CommandType.*;
import classes.Game;
import static Enums.GameState.*;
import classes.Message;

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

    private static Game game = new Game();

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
                          //  sendObject(message);
                //} catch (IOException | EncodeException e) {
                  //  e.printStackTrace();
                //}
            //}
        //});
    }

    private void handleCommand(Message msg, Session session){
        switch(msg.getCommandType()){
            case Register:
                //code here
                msg.getUser().setSession(session);
                game.addPlayer(msg.getUser());
                if(game.getGameState() == Ready){
                    game.startGame();
                    Message toReturn = game.startGame();
                    toReturn.getUser().getSession().getAsyncRemote().sendObject(toReturn);
                }
                else{
                    Message toReturn = new Message();
                    toReturn.setFrom("Server");
                    toReturn.setCommandType(Register);
                    toReturn.setContent("Waiting for other player");
                    msg.getUser().getSession().getAsyncRemote().sendObject(toReturn);
                }
                break;

            case Turn:
                //code here
                List<Message> messages = game.updateBoard(msg);
                for (Message message:messages) {
                    message.getUser().getSession().getAsyncRemote().sendObject(message);
                }
                break;
        }
    }
}

