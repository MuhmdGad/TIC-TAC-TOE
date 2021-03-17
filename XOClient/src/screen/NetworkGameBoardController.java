/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.ScaleTransition;
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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;


public class NetworkGameBoardController implements Initializable, Runnable {

    @FXML
    private Label player1, player2, player1Symbol, player2Symbol;

    @FXML
    private Button Btn1, Btn2, Btn3, Btn4, Btn5, Btn6, Btn7, Btn8, Btn9;
    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Media media;
    @FXML
    private Label winner_loser_txt;
    @FXML
    private Button Done_Btn;
    @FXML
    private GridPane Btns;
    @FXML
    private Pane pane2;
    int player1Score = -1;
    int player2Score = -1;
    @FXML
    private ImageView label_img;
    int s;
    String name;
    String opponent, mainPlayer;
    public static Thread th;
    public static boolean isPlayThreadOn = false;
    String[] parsedMsg;
    boolean onlineFlag = true;

    private String startGame;
    private int xoWinner = 0;
    private int xCount = 0;
    private int oCount = 0;
    int butttonUsed[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int check = 0;//remove it after the trial
    //0 first player
    //1 second player
    //empty
    int gameState[] = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    
    int[][] winningPositions = {
                            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                            {0, 4, 8}, {2, 4, 6}
                               };
    
    String gameMoves = "REC#";
    boolean secPlayer = false;
    boolean recFlag = false;
    String path;

    
    public void setText(String text1, String text2, String text3, String text4, String opponent, String player, int score) {
        player1.setText(text1);
        player2.setText(text2);
        player1Symbol.setText(text3);
        player2Symbol.setText(text4);
        name = text1;
        startGame = text3;
        this.opponent = opponent;
        mainPlayer = player;
        player1Score = score;

        if (opponent.equals(text1)) {
            name = player;
            secPlayer = true;
            disable();
            player2Score = score;
        }
        SignIN2Controller.ps.println("PLN#" + mainPlayer);
        SignIN2Controller.ps.println("PLN#" + opponent);
        gameMoves += name + "#";
        gameMoves += text1 + "#" + text2 + "#";
        recReq(name);
    }
    ///////////////////////////////////Start///////////////////////////////////////////

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        isPlayThreadOn = true;
        pane2.setVisible(false);
        th = new Thread(this);
        th.start();

    }

    @FXML
    private void Done_btn(ActionEvent event) {
        try {
            SignIN2Controller.ps.println("NPLN#" + opponent);
            mediaPlayer.stop();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/ENTER.fxml"));
            Parent viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            ENTERController controller = loader.getController();
            
            if (!secPlayer) {
                controller.nPlayerScore(player1Score);
                //SignIN2Controller.ps.println("SCR#" + mainPlayer + "#" + player1Score);
            } else {
                controller.nPlayerScore(player2Score);
                //SignIN2Controller.ps.println("SCR#" + mainPlayer + "#" + player2Score);
            }
            controller.nPlayerName(name);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            mediaPlayer.stop();
            SignIN2Controller.returnToMainPage(Done_Btn);
            mediaPlayer.stop();
            SignIN2Controller.whenServerOff();
            mediaPlayer.stop();
        } finally {
            th.stop();
        }
    }

    @FXML
    private void Btn(ActionEvent event) {
        
        
        SignIN2Controller.ps.println("ISONLINE#" + opponent);
        Button btn = (Button) event.getSource();
        String[] ID = btn.getId().split("n");
        int number = Integer.parseInt(ID[1]);
        if (butttonUsed[number - 1] == 0) {
            btn.setText(startGame);
            gameMoves += number + ".";
            gameMoves += startGame;
            gameMoves += ".";
            SignIN2Controller.ps.println("GAME#SIGN#" + startGame + "#POS#" + number + "#" + opponent + "#" + xoWinner);
            butttonUsed[number - 1] = 1;
            gameState[number - 1] = xoWinner;
            winnerGame();
            choese();

            disable();
        }

    }

    private void choese() {
        if (startGame.equalsIgnoreCase("X")) {
            xoWinner = 1;
            startGame = "O";
        } else {
            xoWinner = 0;
            startGame = "X";
        }
    }

    private void winnerGame() {
        boolean flag = true;
        for (int[] win : winningPositions) {
            if (gameState[win[0]] == gameState[win[1]] && gameState[win[1]] == gameState[win[2]] && gameState[win[0]] != 2) {
                flag = false;
                if (xoWinner == 0) {
                    player1Score++;
                    player2Score--;
                    if (player2Score < 0) {
                        player2Score = 0;
                    }

                } else if (xoWinner == 1) {
                    player2Score++;
                    player1Score--;
                    if (player1Score < 0) {
                        player1Score = 0;
                    }

                }
                if (!secPlayer) {
                    SignIN2Controller.ps.println("SCR#" + mainPlayer + "#" + player1Score);
                } else {
                    SignIN2Controller.ps.println("SCR#" + mainPlayer + "#" + player2Score);
                }
                if (recFlag) {
                    SignIN2Controller.ps.println(gameMoves);
                }
                disable();
                VidioShow();
            }
        }
        if (isDraw() && flag) {
            path = "/screen/drawVideo.mp4";
            Show(path);
            xoWinner = 2;
            if (recFlag) {
                SignIN2Controller.ps.println(gameMoves);
            }
            disable();

            VidioShow();

        }

    }

    public Button getBtn(int id) {
        Button btn;
        switch (id) {
            case 1:  btn = Btn1;   break;
            case 2:  btn = Btn2;   break;
            case 3:  btn = Btn3;   break;
            case 4:  btn = Btn4;   break;
            case 5:  btn = Btn5;   break;
            case 6:  btn = Btn6;   break;
            case 7:  btn = Btn7;   break;
            case 8:  btn = Btn8;   break;
            case 9:  btn = Btn9;   break;
            default: btn = Btn1;   break;

        }
        return btn;
    }

    public boolean isDraw() {
        boolean draw = true;
        for (int btn : butttonUsed) {
            if (btn != 1) {
                draw = false;
            }
        }
        return draw;
    }

    public void VidioShow() {

        switch (xoWinner) {
            case 0: {

                winner_loser_txt.setText(player1.getText() + " is winner");
                path = "/screen/winningVideo.mp4";

                if (secPlayer) {
                    winner_loser_txt.setText(player2.getText() + " is loser");
                    path = "/screen/lossingVideo.mp4";

                }
                Show(path);
                break;
            }

            case 1: {
                winner_loser_txt.setText(player2.getText() + " is winner");
                String path = "/screen/winningVideo.mp4";

                if (!secPlayer) {
                    winner_loser_txt.setText(player1.getText() + " is loser");
                    path = "/screen/lossingVideo.mp4";

                }
                Show(path);
                break;
            }
            case 2: {
                winner_loser_txt.setText("that's a draw");
                path = "/screen/drawVideo.mp4";
                Show(path);
                break;
            }

        }

            }

    public void Show(String path) {

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameBordController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        pane2.setVisible(true);
                        Done_Btn.setVisible(true);
                        winner_loser_txt.setVisible(true);
                        label_img.setVisible(true);
                        animateUsingScaleTransition(label_img);
                        mediaView.setVisible(true);
                        Btns.setVisible(false);
                        media = new Media(this.getClass().getResource(path).toExternalForm());
                        mediaPlayer = new MediaPlayer(media);
                        mediaView.setMediaPlayer(mediaPlayer);
                        mediaPlayer.setAutoPlay(true);
                    }
                });
            }
        }).start();

    }

    public void disable() {
        Btn1.setDisable(true);
        Btn2.setDisable(true);
        Btn3.setDisable(true);
        Btn4.setDisable(true);
        Btn5.setDisable(true);
        Btn6.setDisable(true);
        Btn7.setDisable(true);
        Btn8.setDisable(true);
        Btn9.setDisable(true);
    }

    public void enableBtns() {
        Btn1.setDisable(false);
        Btn2.setDisable(false);
        Btn3.setDisable(false);
        Btn4.setDisable(false);
        Btn5.setDisable(false);
        Btn6.setDisable(false);
        Btn7.setDisable(false);
        Btn8.setDisable(false);
        Btn9.setDisable(false);
    }

    public void color(Button btn1, Button btn2, Button btn3) {
        btn1.setStyle("-fx-background-color: #cc00cc");
        btn2.setStyle("-fx-background-color: #cc00cc");
        btn3.setStyle("-fx-background-color: #cc00cc");

    }

    @Override
    public void run() {
        while (onlineFlag) {
            String msg;
            String sign;
            int pos;
            try {
                msg = SignIN2Controller.dis.readLine();
                parsing(msg);
                if (parsedMsg[0].equals("OFF") && parsedMsg[1].equals(opponent)) {//don't forget to send the name of the opponent 
                    downOpponent();
                    disable();
                    break;
                } else if (parsedMsg[0].equals("GAME") && parsedMsg[5].equals(mainPlayer)) {
                    //xoWinner=1;
                    enableBtns();
                    sign = parsedMsg[2];
                    pos = Integer.parseInt(parsedMsg[4]);
                    butttonUsed[pos - 1] = 1;
                    gameState[pos - 1] = xoWinner;
                    Platform.runLater(() -> {
                        Button btn;
                        btn = getBtn(pos);
                        btn.setText(sign);
                        gameMoves += pos + ".";
                        gameMoves += sign;
                        gameMoves += ".";

                        winnerGame();
                        choese();
                    });
                }
            } catch (IOException ex) {
                SignIN2Controller.returnToMainPage(Btns);
                SignIN2Controller.whenServerOff();
            }
        }
    }

    public void downOpponent() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Opponent withdraw");
                alert.setContentText("You won, your opponent has been withdrawn");
                alert.show();
                ButtonType buttonTypeAccept = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
                move("DOWN");
            }
        });

    }

    public void move(String calledFrom) {
        try {
            SignIN2Controller.ps.println("NPLN#" + mainPlayer);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/ENTER.fxml"));
            Parent viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            ENTERController controller = loader.getController();
            if (!secPlayer) {
                if (calledFrom.equals("DOWN")) {
                    player1Score++;
                }
                controller.nPlayerScore(player1Score);
                SignIN2Controller.ps.println("SCR#" + mainPlayer + "#" + player1Score);

            } else {
                if (calledFrom.equals("DOWN")) {
                    player2Score++;
                }
                controller.nPlayerScore(player2Score);
                SignIN2Controller.ps.println("SCR#" + mainPlayer + "#" + player2Score);

            }
            controller.nPlayerName(name);

            Stage window = (Stage) pane2.getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(NetworkGameBoardController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            th.stop();
        }
    }

    public void parsing(String recievedMsg) {

        if (recievedMsg == (null)) {
            SignIN2Controller.returnToMainPage(Btns);
            SignIN2Controller.whenServerOff();
        } else {
            parsedMsg = recievedMsg.split("\\#");
        }
    }

    public void recReq(String name) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Playing recording");
        alert.setHeaderText("recording Confirmation");
        alert.setContentText("Do you want to record this game?");
        ButtonType buttonTypeAccept = new ButtonType("Accept", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Reject", ButtonBar.ButtonData.CANCEL_CLOSE);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            if (secPlayer) {
                recFlag = true;
            } else {
                recFlag = true;
            }
        } else {
            if (secPlayer) {
                recFlag = false;
            } else {
                recFlag = false;
            }

        }
    }

    private void animateUsingScaleTransition(ImageView heart) {
        ScaleTransition scaleTransition = new ScaleTransition(
                Duration.seconds(1), heart
        );
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setFromZ(1);
        scaleTransition.setToX(1.1);
        scaleTransition.setToY(1.1);
        scaleTransition.setToZ(1.1);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(Animation.INDEFINITE);
        scaleTransition.play();
    }
}
