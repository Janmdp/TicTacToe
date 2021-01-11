package ClientEndpoints.Chat;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class ChatEndpoint {
    private static ChatEndpoint instance = null;

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
    public void onWebSocketText(String message, Session session){
        System.out.println("[WebSocket Client message received] " + message);
    }

}
