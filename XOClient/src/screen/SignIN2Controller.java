package screen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import xoClientModel.signINBase;


public class SignIN2Controller implements Initializable {

    @FXML
    private TextField userName_txt;
    @FXML
    private Button signIN_btn;
    @FXML
    private Button signUP_btn;
    @FXML
    private PasswordField password_txt;
    public static Socket sClient;
    public static DataInputStream dis;
    public static PrintStream ps;
    String ip;
    Thread signInThread;
    private int score = 0;
    String[] parsedMsg;

    /**
     * Initializes the controller class.
     */
    public void ip_value(String IP) {
        ip = IP;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            sClient = new Socket(SignINController.serverIP, 5050);
            dis = new DataInputStream(sClient.getInputStream());
            ps = new PrintStream(sClient.getOutputStream());
        } catch (IOException ex) {
            SignIN2Controller.whenServerOff();
            SignIN2Controller.returnToMainPage(password_txt);
            System.out.println("initialize signin2");
        }
        // TODO

    }

    @FXML
    private void signUP_click(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/signUP.fxml"));
            Parent viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            SignUPController controller = loader.getController();
            controller.ip_value(ip);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(SignIN2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML

    private void signIN_click(ActionEvent event) {
        String name = userName_txt.getText();
        String pass = password_txt.getText();
        if (name.isEmpty() || pass.isEmpty() || name.contains(" ") || pass.contains(" ")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Data here is required without spaces.", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
            alert.show();
        } else {
            String Data = "SIN#" + name + "#" + pass;
            System.out.println(Data);
            ps.println(Data);
            ps.flush();

            signInThread = new Thread(new Runnable() {
                public void run() {

                    try {
                        String msg = dis.readLine();
                        System.out.println(" my massege is ....." + msg);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                SignInParser(msg);
                                if (parsedMsg[0].equals("SignIN not Confirmed")) {
                                    if (parsedMsg[1].equals("User is online")) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR, "Username is already logged in", ButtonType.OK);
                                        alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
                                        alert.show();
                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password", ButtonType.OK);
                                        alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
                                        alert.show();
                                    }
                                } else {

                                    try {
                                        FXMLLoader loader = new FXMLLoader();
                                        loader.setLocation(getClass().getResource("/xoClientView/ENTER.fxml"));
                                        Parent viewParent;
                                        viewParent = loader.load();
                                        Scene viewscene = new Scene(viewParent);
                                        ENTERController controller = loader.getController();
                                        controller.nPlayerName(userName_txt.getText());
                                        controller.nPlayerScore(score);
                                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                        window.setScene(viewscene);
                                        window.show();
                                    } catch (IOException ex) {
                                        Logger.getLogger(SignIN2Controller.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                            }
                        });
                    } catch (IOException ex) {
                        whenServerOff();
                        returnToMainPage(signIN_btn);
                        System.out.println("signin2 signin click");
                    } finally {
                        signInThread.stop();
                    }
                }
            });
            signInThread.start();
        }

    }

    @FXML
    private void back_click(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/xoClientView/signIN.fxml"));
        Parent viewParent;
        try {
            viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            SignINController controller = loader.getController();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(SignIN2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void SignInParser(String msg) {
        parsedMsg = msg.split("\\#");
        if (parsedMsg[0].equals("SignIN Confirmed")) {
            score = Integer.parseInt(parsedMsg[1]);
        }
    }

    public static void whenServerOff() {
        try {
            SignIN2Controller.dis.close();
            SignIN2Controller.ps.close();
            SignIN2Controller.sClient.close();
            if (FreeOnlinePlayersController.isReplyThreadOn) {
                FreeOnlinePlayersController.replyThread.stop();
            }
            if (FreeOnlinePlayersController.isRequestThreadOn) {
                FreeOnlinePlayersController.requestThread.stop();
            }
            if (NetworkGameBoardController.isPlayThreadOn) {
                NetworkGameBoardController.th.stop();
            }
        } catch (IOException ex) {
            Logger.getLogger(SignIN2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void returnToMainPage(Node node) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Server is disconnected", ButtonType.OK);
                    alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
                    alert.show();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/xoClientView/signIN.fxml"));
                    Parent viewparent = loader.load();
                    Scene viewscene = new Scene(viewparent);
                    Stage window = (Stage) node.getScene().getWindow();
                    window.setScene(viewscene);
                    window.show();
                } catch (IOException ex) {
                    Platform.exit();
                    System.out.println("RETURN TO MAIN PAGE");
                }
            }
        });
    }
}