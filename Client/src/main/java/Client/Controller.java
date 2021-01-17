package Client;

import static Enums.CommandType.Register;
import static Enums.CommandType.Turn;
import static Enums.SquareState.*;

import ClientEndpoints.ClientEndpoint;
import Enums.SquareState;
import classes.Message;
import classes.User;
import User.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    UserData userData = new UserData();
    User user;
    private ClientEndpoint clientEndpoint;
    private SquareState[] board = new SquareState[9];
    Image circle;
    Image cross;
    Button[] buttons = new Button[9];
    public TabPane tPane;
    public Button btn0;
    public Button btn1;
    public Button btn2;
    public Button btn3;
    public Button btn4;
    public Button btn5;
    public Button btn6;
    public Button btn7;
    public Button btn8;
    public TextField tboxName;
    public PasswordField tboxPassword;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        circle = new Image("https://image.flaticon.com/icons/png/512/32/32341.png");
        cross = new Image("https://static.thenounproject.com/png/1479017-200.png");
        buttons[0] = btn0;
        buttons[1] = btn1;
        buttons[2] = btn2;
        buttons[3] = btn3;
        buttons[4] = btn4;
        buttons[5] = btn5;
        buttons[6] = btn6;
        buttons[7] = btn7;
        buttons[8] = btn8;

        board[0] = Empty;
        board[1] = Empty;
        board[2] = Empty;
        board[3] = Empty;
        board[4] = Empty;
        board[5] = Empty;
        board[6] = Empty;
        board[7] = Empty;
        board[8] = Empty;

        toggleButtons(false);

    }

    private void connectToServer(){
        clientEndpoint = new ClientEndpoint();
        clientEndpoint.setController(this);
        clientEndpoint.start();
        Message msg = new Message();
        msg.setFrom("Client");
        msg.setCommandType(Register);
        msg.setContent("Registering to server");
        msg.setUser(user);
        clientEndpoint.sendMessageToServer(msg);
    }

    public ImageView getImage(SquareState squareState){
        if(squareState == Circle){
            ImageView imgView = new ImageView(circle);
            imgView.setFitHeight(80);
            imgView.setPreserveRatio(true);
            return imgView;
        }
        else if(squareState == Cross){
            ImageView imgView = new ImageView(cross);
            imgView.setFitHeight(80);
            imgView.setPreserveRatio(true);
            return imgView;
        }
        else{
            ImageView imgView = new ImageView();
            imgView.setFitHeight(80);
            imgView.setPreserveRatio(true);
            return imgView;
        }

    }

    public void showMessage(String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,content, ButtonType.YES);
        alert.show();
    }

    public void updateSquare(Message message){
        buttons[message.getLocation()].setGraphic(getImage(message.getSquareState()));
        buttons[message.getLocation()].setDisable(true);
        board[message.getLocation()] = message.getSquareState();
    }


    public void ClaimSquare(ActionEvent actionEvent) {
        System.out.println("Method triggered");
        Button clicked = (Button) actionEvent.getSource();
        for(int i = 0; i < buttons.length; i++){
            if(buttons[i] == clicked){
                //Send coordinate to server
                System.out.println("matched button: " + i);
                Message msg = new Message();
                msg.setFrom("Client");
                msg.setCommandType(Turn);
                msg.setUser(user);
                msg.setContent("User did something");
                msg.setLocation(i);
                clientEndpoint.sendMessageToServer(msg);
            }
        }
    }

    public void toggleButtons(boolean turn){
        for(int i = 0; i < buttons.length; i++){
            if(turn && board[i] == Empty){
                buttons[i].setDisable(false);
            }
            else{
                buttons[i].setDisable(true);
            }
        }
    }

    public void tryLogin(ActionEvent actionEvent) throws IOException, InterruptedException {
        String name = tboxName.getText();
        String password = tboxPassword.getText();
        user = userData.tryLogin(name, password);
        if(user != null){
            showMessage("Succesfully logged in!");
            connectToServer();
            tPane.getSelectionModel().select(1);
        }
        else{
            showMessage("Login attempt failed!");
        }
    }
}
