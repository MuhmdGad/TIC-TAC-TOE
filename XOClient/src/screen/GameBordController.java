package screen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
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
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Taha
 */
public class GameBordController implements Initializable {

    @FXML
    private Button Btn1, Btn2, Btn3, Btn4, Btn5, Btn6, Btn7, Btn8, Btn9;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player1Symbol;
    @FXML
    private Label player2Symbol;

    private String[] Steps;
    private String[] BeginSymbol;
    private String[] playerinfo;
    String Data;

    public void setText(String text1, String text2, String text3, String text4) {
        player1.setText(text1);
        player2.setText(text2);
        player1Symbol.setText(text3);
        player2Symbol.setText(text4);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public String[] parsing1(String Message) {
        String[] arrOfStr = Message.split("#");
        return arrOfStr;

    }

    public String[] parsing2(String Message) {
        String[] arrOfStr = Message.split("&");
        return arrOfStr;
    }

    public String[] parsing3(String Message) {
        String[] arrOfStr = Message.split("@");
        return arrOfStr;
    }

    public void GameRecord(String line) {
        Data = line;
        set_Game(Data);
        System.out.println("done");

    }

    public void set_Game(String Message) {
        new Thread(new Runnable() {
            public void run() {
                boolean flag;
                Steps = parsing1(Message);
                BeginSymbol = parsing2(Steps[Steps.length - 1]);
                playerinfo = parsing3(BeginSymbol[1]);
                player1.setText(playerinfo[0]);
                player1Symbol.setText(playerinfo[1]);
                player2.setText(playerinfo[2]);
                player2Symbol.setText(playerinfo[3]);
                System.out.println(BeginSymbol[0] + "--------------------" + BeginSymbol[1]);
                if (BeginSymbol[0].equals("X")) {
                    flag = true;
                } else {
                    flag = false;
                }
                for (int i = 0; i < Steps.length - 1; i++) {
                    switch (Steps[i]) {
                        case "1":
                            if (flag) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn1.setText("X");
                                        Btn1.setDisable(true);
                                    }
                                });
                            } else {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn1.setText("O");
                                        Btn1.setDisable(true);
                                    }
                                });
                            }
                            break;
                        case "2":
                            if (flag) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn2.setText("X");
                                        Btn2.setDisable(true);
                                    }
                                });
                            } else {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn2.setText("O");
                                        Btn2.setDisable(true);
                                    }
                                });
                            }
                            break;
                        case "3":
                            if (flag) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn3.setText("X");
                                        Btn3.setDisable(true);
                                    }
                                });
                            } else {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn3.setText("O");
                                        Btn3.setDisable(true);
                                    }
                                });
                            }
                            break;
                        case "4":
                            if (flag) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn4.setText("X");
                                        Btn4.setDisable(true);
                                    }
                                });
                            } else {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn4.setText("O");
                                        Btn4.setDisable(true);
                                    }
                                });
                            }
                            break;
                        case "5":
                            if (flag) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn5.setText("X");
                                        Btn5.setDisable(true);
                                    }
                                });
                            } else {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn5.setText("O");
                                        Btn5.setDisable(true);
                                    }
                                });
                            }
                            break;
                        case "6":
                            if (flag) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn6.setText("X");
                                        Btn6.setDisable(true);
                                    }
                                });
                            } else {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn6.setText("O");
                                        Btn6.setDisable(true);
                                    }
                                });
                            }
                            break;
                        case "7":
                            if (flag) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn7.setText("X");
                                        Btn7.setDisable(true);
                                    }
                                });
                            } else {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn7.setText("O");
                                        Btn7.setDisable(true);
                                    }
                                });
                            }
                            break;
                        case "8":
                            if (flag) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn8.setText("X");
                                        Btn8.setDisable(true);
                                    }
                                });
                            } else {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn8.setText("O");
                                        Btn8.setDisable(true);
                                    }
                                });
                            }
                            break;
                        case "9":
                            if (flag) {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn9.setText("X");
                                        Btn9.setDisable(true);
                                    }
                                });
                            } else {
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        Btn9.setText("O");
                                        Btn9.setDisable(true);
                                    }
                                });
                            }
                            break;
                        default:
                            break;
                    }

                    if (flag) {
                        flag = false;
                    } else {
                        flag = true;
                    }
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GameBordController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                set_color();
            }
        }).start();

    }

    public void set_color() {
        
                    if (Btn1.getText().equals(Btn2.getText()) && Btn2.getText().equals(Btn3.getText()) && !Btn1.getText().isEmpty()) { color(Btn1, Btn2, Btn3); }
               else if (Btn4.getText().equals(Btn5.getText()) && Btn5.getText().equals(Btn6.getText()) && !Btn4.getText().isEmpty()) { color(Btn4, Btn5, Btn6); }
               else if (Btn7.getText().equals(Btn8.getText()) && Btn8.getText().equals(Btn9.getText()) && !Btn7.getText().isEmpty()) { color(Btn7, Btn8, Btn9); }
               else if (Btn1.getText().equals(Btn4.getText()) && Btn4.getText().equals(Btn7.getText()) && !Btn1.getText().isEmpty()) { color(Btn1, Btn4, Btn7); } 
               else if (Btn2.getText().equals(Btn5.getText()) && Btn5.getText().equals(Btn8.getText()) && !Btn2.getText().isEmpty()) { color(Btn2, Btn5, Btn8); } 
               else if (Btn3.getText().equals(Btn6.getText()) && Btn6.getText().equals(Btn9.getText()) && !Btn3.getText().isEmpty()) { color(Btn3, Btn6, Btn9); }
               else if (Btn1.getText().equals(Btn5.getText()) && Btn5.getText().equals(Btn9.getText()) && !Btn1.getText().isEmpty()) { color(Btn1, Btn5, Btn9); }
               else if (Btn3.getText().equals(Btn5.getText()) && Btn5.getText().equals(Btn7.getText()) && !Btn3.getText().isEmpty()) { color(Btn3, Btn5, Btn7); }
               
    }

    public void color(Button b1, Button b2, Button b3) {
        b1.setStyle("-fx-background-color: red");
        b2.setStyle("-fx-background-color: red");
        b3.setStyle("-fx-background-color: red");
    }

    @FXML
    private void Back_btn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/record.fxml"));
            Parent viewparent = loader.load();
            Scene viewscene = new Scene(viewparent);
            RecordController controller = loader.getController();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(GameBordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
