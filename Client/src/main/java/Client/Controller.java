package Client;

import static Enums.CommandType.Register;
import static Enums.CommandType.Turn;
import static Enums.SquareState.*;

import ClientEndpoints.ClientEndpoint;
import Enums.SquareState;
import classes.Message;
import classes.Position;
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
    private SquareState[][] board = new SquareState[3][3];
    private boolean singleplayer = false;
    Image circle;
    Image cross;
    Button[][] buttons = new Button[3][3];
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
    public CheckBox cBoxSingle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        circle = new Image("https://image.flaticon.com/icons/png/512/32/32341.png");
        cross = new Image("https://static.thenounproject.com/png/1479017-200.png");
        buttons[0][0] = btn0;
        buttons[0][1] = btn1;
        buttons[0][2] = btn2;
        buttons[1][0] = btn3;
        buttons[1][1] = btn4;
        buttons[1][2] = btn5;
        buttons[2][0] = btn6;
        buttons[2][1] = btn7;
        buttons[2][2] = btn8;

        for(int row = 0; row < buttons.length; row++){
            String s ="";
            for(int col = 0; col < buttons.length; col++){
                s = s + buttons[row][col].getId() + " | ";
            }
            System.out.println(s);
            System.out.println("--------------------");
        }


        board[0][0] = Empty;
        board[0][1] = Empty;
        board[0][2] = Empty;
        board[1][0] = Empty;
        board[1][1] = Empty;
        board[1][2] = Empty;
        board[2][0] = Empty;
        board[2][1] = Empty;
        board[2][2] = Empty;

        toggleButtons(false);

    }

    private void connectToServer(){
        clientEndpoint = new ClientEndpoint();
        clientEndpoint.setController(this);
        clientEndpoint.start();
        Message msg = new Message();
        msg.setFrom("Client");
        msg.setCommandType(Register);
        msg.setSingleplayer(singleplayer);
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
        buttons[message.getPosition().getPosX()][message.getPosition().getPosY()].setGraphic(getImage(message.getSquareState()));
        buttons[message.getPosition().getPosX()][message.getPosition().getPosY()].setDisable(true);
        board[message.getPosition().getPosX()][message.getPosition().getPosY()] = message.getSquareState();
    }


    public void ClaimSquare(ActionEvent actionEvent) {
        Button clicked = (Button) actionEvent.getSource();
        for(int row = 0; row < buttons.length; row++){
            for (int col = 0; col < buttons.length; col++){
                if(buttons[row][col] == clicked){
                    //Send coordinate to server
                    System.out.println("matched button: y=" + row + " x=" + col);
                    Message msg = new Message();
                    msg.setFrom("Client");
                    msg.setCommandType(Turn);
                    msg.setUser(user);
                    msg.setContent("User did something");
                    msg.setPosition(new Position(row,col));
                    clientEndpoint.sendMessageToServer(msg);
                }
            }

        }
    }

    public void toggleButtons(boolean turn){
        for(int row = 0; row < buttons.length; row++){
            for(int col = 0; col < buttons.length; col++){
                if(turn && board[row][col] == Empty){
                    buttons[row][col].setDisable(false);
                }
                else{
                    buttons[row][col].setDisable(true);
                }
            }

        }
    }

    public void tryLogin(ActionEvent actionEvent) throws IOException, InterruptedException {
        login(tboxName.getText(), tboxPassword.getText());
    }

    private void login(String name, String password) throws IOException, InterruptedException {
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
    public void registerUser(ActionEvent actionEvent) throws IOException, InterruptedException {
        String name = tboxName.getText();
        String password = tboxPassword.getText();
        if(userData.register(name,password)){
            login(name,password);
        }

    }

    public void endGame(boolean turn){
        toggleButtons(turn);
    }

    public void toggleSingleplayer(ActionEvent actionEvent) {
        if(cBoxSingle.isSelected()){
            singleplayer = true;
        }
        else{
            singleplayer= false;
        }
    }
}
