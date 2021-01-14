import ClientEndpoints.ClientEndpoint;
import classes.Message;
import classes.User;

import static Enums.CommandType.*;

public class TestClass {
    public static void main(String Args[]){
        ClientEndpoint endpoint = new ClientEndpoint();
        endpoint.start();
        User hardCoded = new User(1, "Jan", "Test");
        Message msg = new Message();
        msg.setFrom("Client");
        msg.setCommandType(Register);
        msg.setContent("Registering to server");
        msg.setUser(hardCoded);
        endpoint.sendMessageToServer(msg);

    }
}
