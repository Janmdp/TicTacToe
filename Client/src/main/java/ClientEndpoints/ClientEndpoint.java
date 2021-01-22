package ClientEndpoints;

import Client.Controller;
import Decoders.MessageDecoder;
import Encoders.MessageEncoder;
import Enums.CommandType;
import classes.Message;
import javafx.application.Platform;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@javax.websocket.ClientEndpoint(decoders = {MessageDecoder.class},
        encoders = MessageEncoder.class)
public class ClientEndpoint {
    private static ClientEndpoint instance = null;
    private Controller controller;

    public void setController(Controller controller) { this.controller = controller; }

    /**
     * The local websocket uri to connect to.
     */
    private final String uri = "ws://localhost:8095/chat";
    private Session session;

    // Status of the webSocket client
    boolean isRunning = false;


    public void start(){
        System.out.println("[WebSocket Client start connection]");
        if (!isRunning) {
            startClient();
            isRunning = true;
        }
    }
    private void startClient() {
        System.out.println("[WebSocket Client start]");
        try {
            System.out.println("1");
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, new URI(uri));

        } catch (IOException | URISyntaxException | DeploymentException ex) {
            // do something useful eventually
            System.out.println("2");
            ex.printStackTrace();
        }
    }

    @OnOpen
    public void onWebSocketConnect(Session session){
        System.out.println("[WebSocket Client open session] " + session.getRequestURI());
        this.session = session;
    }

    @OnMessage
    public void onWebSocketText(Message message, Session session){
        //System.out.println("[WebSocket Client message received] " + message );
        handleCommand(message, session);
    }

    public void sendMessageToServer(Message message) {
        // Use asynchronous communication
        session.getAsyncRemote().sendObject(message);
    }

    private void handleCommand(Message message, Session session){
        CommandType cType = message.getCommandType();
        switch (cType){
            case Register:
                //code here
                System.out.println("[WebSocket Client message received] " + message.getContent() );
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controller.showMessage(message.getContent());
                    }
                });
                break;

            case Turn:
                //code here
                System.out.println("[WebSocket Client message received] " + message.getContent() );
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controller.showMessage(message.getContent());
                        if(message.getPosition() != null){
                            controller.updateSquare(message);
                        }
                        controller.toggleButtons(message.getUser().isTurn());
                    }
                });
                break;

            case EndGame:
                //code here
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        controller.showMessage(message.getContent());
                        controller.endGame(message.getUser().isTurn());
                    }
                });
                break;
        }
    }

}
