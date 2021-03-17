package screen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ENTERController implements Initializable {

    @FXML
    private Label playerName;
    @FXML
    private Label score;
    @FXML
    private Button newGame_txt;
    @FXML
    private Button record_txt;
    public static String Name;
    public static Thread replyThreadEnter;
    public static boolean isReplyThreadEnterOn;
    String[] parsedMsg;
    public static int playerScore;
    public static boolean isOnline = false;

    public void nPlayerName(String name) {
        Name = name;
        playerName.setText(Name);
    }

    public void nPlayerScore(int scr) {
        playerScore = scr;
        score.setText("" + scr);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isOnline = true;
        isReplyThreadEnterOn = false;
        getPlayRequest();
    }

    @FXML
    private void recordAction(ActionEvent event) throws IOException {
        replyThreadEnter.stop();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/xoClientView/NetworkRecord.fxml"));
        Parent viewParent = loader.load();
        Scene viewscene = new Scene(viewParent);
        screen.NetworkRecordController controller = loader.getController();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(viewscene);
        window.show();
    }

    @FXML
    private void log_out(ActionEvent event) {
        try {
            String logoutMessage = "SOUT#" + Name;
            SignIN2Controller.ps.println(logoutMessage);
            SignIN2Controller.dis.close();
            SignIN2Controller.ps.close();
            SignIN2Controller.sClient.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/signIN.fxml"));
            Parent viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            SignINController controller = loader.getController();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
            replyThreadEnter.stop();
            if (NetworkRecordController.isThreadOn) {
                NetworkRecordController.getRecThread.stop();
            }
        } catch (IOException ex) {
            Logger.getLogger(ENTERController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void NewGameAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/FreeOnlinePlayers.fxml"));
            Parent viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            FreeOnlinePlayersController controller = loader.getController();
            controller.set_playerName(Name);
            controller.setScore(playerScore);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            SignIN2Controller.whenServerOff();
            SignIN2Controller.returnToMainPage(newGame_txt);
        }
    }

    public void getPlayRequest() {
        replyThreadEnter = new Thread(new Runnable() {
            @Override
            public void run() {
                isReplyThreadEnterOn = true;
                while (true) {
                    try {
                        String msg = SignIN2Controller.dis.readLine();
                        parsing(msg);
                        if (parsedMsg[0].equals("DUWTP") && parsedMsg[1].equals(Name)) {
                            final String oppName = parsedMsg[2];
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (confirmationToPlay(oppName) == true) {
                                        String sentMsg = new String("PREQ#accept#" + oppName + "#" + Name);
                                        SignIN2Controller.ps.println(sentMsg);
                                        Platform.runLater(new Runnable() {
                                            public void run() {
                                                try {
                                                    showBoardForOpponent(oppName, Name);
                                                } finally {
                                                    replyThreadEnter.stop();
                                                }
                                            }
                                        });
                                    } else {
                                        String sentMsg = new String("PREQ#reject#" + oppName + "#" + Name);
                                        SignIN2Controller.ps.println(sentMsg);
                                    }
                                }
                            });
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        SignIN2Controller.whenServerOff();
                    }
                }
            }
        });
        replyThreadEnter.start();
    }

    public void parsing(String recievedMsg) {
        if (recievedMsg == (null)) {
            SignIN2Controller.returnToMainPage(playerName);
            SignIN2Controller.whenServerOff();
        } else {
            parsedMsg = recievedMsg.split("\\#");
        }
    }

    public boolean confirmationToPlay(String opp) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Playing Confirmation");
        alert.setHeaderText("Playing Confirmation");
        alert.setContentText("Do you want to play with " + opp + " ?");
        ButtonType buttonTypeAccept = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            return false;
        }
    }

    public void showBoardForOpponent(String opp, String mainPlayer) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/NetworkGameBoard.fxml"));
            Parent viewparent = loader.load();
            Scene viewscene = new Scene(viewparent);
            NetworkGameBoardController controller = loader.getController();
            controller.setText(opp, mainPlayer, "X", "O", opp, mainPlayer, playerScore);
            Stage window = (Stage) playerName.getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(ENTERController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
