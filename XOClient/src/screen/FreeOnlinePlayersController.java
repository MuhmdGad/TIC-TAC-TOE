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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javax.swing.JPanel;
import static screen.ENTERController.replyThreadEnter;


public class FreeOnlinePlayersController implements Initializable {

    ObservableList onlineList = FXCollections.observableArrayList();
    ObservableList playingList = FXCollections.observableArrayList();
    @FXML
    private javafx.scene.control.ListView listViewOnline;
    @FXML
    private javafx.scene.control.ListView listViewPlaying;
    String userName;
    String[] parsedMsg;
    String[] parsedOnlineList;
    String[] parsedPlayingList;
    boolean flag = false;
    Socket s2;
    DataInputStream dis2;
    PrintStream ps2;
    public static Thread requestThread;
    public static Thread replyThread;
    public static boolean isRequestThreadOn = false;
    public static boolean isReplyThreadOn = false;
    int score;
    @FXML
    private ProgressIndicator waitingIndicator;

    public void set_playerName(String name) {
        userName = ENTERController.Name;
    }

    public void setScore(int scr) {
        score = scr;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            replyThreadEnter.stop();
            listViewPlaying.setMouseTransparent(true);
            listViewPlaying.setFocusTraversable(false);
            waitingIndicator.setVisible(false);
            s2 = new Socket(SignINController.serverIP, 5050);
            dis2 = new DataInputStream(s2.getInputStream());
            ps2 = new PrintStream(s2.getOutputStream());
            getPlayerListAndPlayRequest();
        } catch (IOException ex) {
            SignIN2Controller.whenServerOff();
            SignIN2Controller.returnToMainPage(listViewOnline);
        }
    }

    private void loadOnlineToListView() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                onlineList.removeAll(onlineList);
                listViewOnline.getItems().clear();
                for (String s : parsedOnlineList) {
                    if (s.equals(userName) || s.equals("PLIST") || s.equals("LIST") || s.equals("IST")) {
                        continue;
                    }
                    listViewOnline.setMouseTransparent(false);
                    listViewOnline.setFocusTraversable(true);
                    onlineList.add(s);
                }
                if (onlineList.isEmpty()) {
                    onlineList.add("No one is Currently Online");
                    listViewOnline.setMouseTransparent(true);
                    listViewOnline.setFocusTraversable(false);
                }
                listViewOnline.getItems().addAll(onlineList);
            }
        });
    }

    private void loadPlayingToListView() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                playingList.removeAll(playingList);
                listViewPlaying.getItems().clear();
                for (String s : parsedPlayingList) {
                    if (s.equals(userName) || s.equals("PLIST") || s.equals("LIST") || s.equals("IST")) {
                        continue;
                    }
                    playingList.add(s);
                }
                if (playingList.get(0).equals("null")) {
                    playingList.remove(0);
                    playingList.add("No one is Currently playing");
                }
                listViewPlaying.getItems().addAll(playingList);
            }
        });
    }

    @FXML
    private void handleMouseClickAction(javafx.scene.input.MouseEvent event) throws IOException {
        String opponentName = (String) listViewOnline.getSelectionModel().getSelectedItem();
        if (opponentName == null || opponentName.isEmpty()) {
        } else {
            listViewOnline.setMouseTransparent(true);
            listViewOnline.setFocusTraversable(false);
            waitingIndicator.setVisible(true);
            waitingIndicator.setProgress(-1);
            requestThread = new Thread() {
                public void run() {
                    isRequestThreadOn = true;
                    String sentMsg = new String("DUWTP#" + opponentName + "#" + userName);
                    ps2.println(sentMsg);
                    int d = 0;
                    while (true) {
                        String recievedReqeustMsg = null;
                        try {
                            recievedReqeustMsg = dis2.readLine();
                            parsing(recievedReqeustMsg);
                        } catch (IOException ex) {
                            SignIN2Controller.returnToMainPage(listViewOnline);
                            SignIN2Controller.whenServerOff();
                        }
                        if (parsedOnlineList[0].equals("PREQ") && parsedOnlineList[1].equals("accept") && parsedOnlineList[2].equals(userName)) {
                            Platform.runLater(new Runnable() {
                                public void run() {
                                    try {
                                        FXMLLoader loader = new FXMLLoader();
                                        loader.setLocation(getClass().getResource("/xoClientView/NetworkGameBoard.fxml"));
                                        Parent viewparent = loader.load();
                                        Scene viewscene = new Scene(viewparent);
                                        NetworkGameBoardController controller = loader.getController();
                                        controller.setText(userName, opponentName, "X", "O", opponentName, userName, score);
                                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                        window.setScene(viewscene);
                                        window.show();

                                    } catch (IOException ex) {
                                        Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
                                    } finally {
                                        try {
                                            dis2.close();
                                            ps2.close();
                                            s2.close();
                                            requestThread.stop();
                                            replyThread.stop();
                                        } catch (IOException ex) {
                                            Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                            });
                        } else if (parsedOnlineList[0].equals("PREQ") && parsedOnlineList[1].equals("reject") && parsedOnlineList[2].equals(userName)) {
                            listViewOnline.getSelectionModel().clearSelection();
                            listViewOnline.setMouseTransparent(false);
                            listViewOnline.setFocusTraversable(true);
                            waitingIndicator.setVisible(false);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    Alert alert = new Alert(Alert.AlertType.ERROR, "The invitation is rejected from " + parsedOnlineList[3], ButtonType.OK);
                                    alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
                                    alert.show();
                                }
                            });
                            requestThread.stop();
                        }
                    }
                }
            };
            requestThread.start();
        }
    }

    @FXML
    private void back_btn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/xoClientView/ENTER.fxml"));
        Parent viewparent = loader.load();
        Scene viewscene = new Scene(viewparent);
        ENTERController controller = loader.getController();
        controller.nPlayerName(userName);
        controller.nPlayerScore(score);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(viewscene);
        window.show();
        dis2.close();
        ps2.close();
        s2.close();
        replyThread.stop();
        if (isRequestThreadOn) {
            requestThread.stop();
        }

    }

    public void getPlayerListAndPlayRequest() {
        replyThread = new Thread(new Runnable() {
            @Override
            public void run() {
                SignIN2Controller.ps.println("PLIST");
                isReplyThreadOn = true;
                while (true) {
                    try {
                        String msg = SignIN2Controller.dis.readLine();
                        parsing(msg);
                        if (parsedOnlineList[0].equals("DUWTP") && parsedOnlineList[1].equals(userName)) {

                            final String oppName = parsedOnlineList[2];
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    if (confirmationToPlay(oppName) == true) {
                                        String sentMsg = new String("PREQ#accept#" + oppName + "#" + userName);
                                        SignIN2Controller.ps.println(sentMsg);
                                        Platform.runLater(new Runnable() {
                                            public void run() {
                                                try {
                                                    showBoardForOpponent(oppName, userName);
                                                } finally {
                                                    replyThread.stop();
                                                }
                                            }
                                        });
                                    } else {
                                        String sentMsg = new String("PREQ#reject#" + oppName + "#" + userName);
                                        System.out.println(sentMsg);
                                    }
                                }
                            });
                        } else if (parsedOnlineList[0].equals("PLIST") || parsedOnlineList[0].equals("LIST") || parsedOnlineList[0].equals("IST")) {
                            loadOnlineToListView();
                            loadPlayingToListView();
                            SignIN2Controller.ps.println("PLIST");
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                        SignIN2Controller.returnToMainPage(listViewOnline);
                        SignIN2Controller.whenServerOff();
                    }
                }
            }
        });
        replyThread.start();
    }

    public void parsing(String recievedMsg) {
        if (recievedMsg.contains(".")) {
            parsedMsg = recievedMsg.split("\\.");
            parsedOnlineList = parsedMsg[0].split("\\#");
            parsedPlayingList = parsedMsg[1].split("\\#");
        } else {
            parsedOnlineList = recievedMsg.split("\\#");
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
            controller.setText(opp, mainPlayer, "X", "O", opp, mainPlayer, score);
            Stage window = (Stage) listViewOnline.getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(FreeOnlinePlayersController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
