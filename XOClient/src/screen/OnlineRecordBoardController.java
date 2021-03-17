/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import screen.FreeOnlinePlayersController;
import screen.GameBordController;
import screen.SignIN2Controller;



public class OnlineRecordBoardController implements Initializable {

    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player1Symbol;
    @FXML
    private Label player2Symbol;
    @FXML
    private Button Btn1;
    @FXML
    private Button Btn4;
    @FXML
    private Button Btn2;
    @FXML
    private Button Btn5;
    @FXML
    private Button Btn7;
    @FXML
    private Button Btn8;
    @FXML
    private Button Btn3;
    @FXML
    private Button Btn6;
    @FXML
    private Button Btn9;
    String gameMoves;
    //String gameMoves = "1#1#5#1#4#1#2#1#7#1";
    String[] parsedMoves=NetworkRecordController.recordParsedMsg;
    Thread recordThread;
    int i = 0;
    Button btn = new Button();
    boolean flag=true;
    @FXML
    private Button backBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO    
        show();

    }

    public void setText(String firstPlayer, String secPlayer,String firstSymbol,String secSymbol){
    player1.setText(firstPlayer);
    player2.setText(secPlayer);
    this.player1Symbol.setText(firstSymbol);
    this.player2Symbol.setText(secSymbol);
    }
    @FXML
    private void Back_btn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/NetworkRecord.fxml"));
            Parent viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            screen.NetworkRecordController controller =loader.getController();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(OnlineRecordBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    public void show (){
   // parser(gameMoves);

        for (i = 0; i < parsedMoves.length-1; i += 2) {
            if(parsedMoves[i + 1].equals("O")){
            flag=false;
            }
            else if(parsedMoves[i + 1].equals("X")){
            flag=true;
            }
            
        }
        
        new Thread(new Runnable() {
            public void run(){
           for(int i = 0; i<parsedMoves.length-1;i+=2)
               { 
                 switch (Integer.parseInt(parsedMoves[i])) {
                            case 1:
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn1.setText("X");
                                       }
                                     });
                            
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn1.setText("O");
                                       }
                                     });
                                break;
                            case 2:
                                if(flag)
                                     Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn2.setText("X");
                                       }
                                     });
                                else
                                     Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn2.setText("O");
                                       }
                                     });
                                break;
                            case 3:
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn3.setText("X");
                                       }
                                     });
                                else
                                   Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn3.setText("O");
                                       }
                                     });
                                break;
                            case 4:
                                if(flag)
                                   Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn4.setText("X");
                                       }
                                     });
                                else
                                   Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn4.setText("O");
                                       }
                                     });
                                break;
                            case 5:
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn5.setText("X");
                                       }
                                     });
                                else
                                     Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn5.setText("O");
                                       }
                                     });
                                break;
                            case 6:
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn6.setText("X");
                                       }
                                     });
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn6.setText("O");
                                       }
                                     });
                                break;
                            case 7:
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn7.setText("X");
                                       }
                                     });
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn7.setText("O");
                                       }
                                     });
                                break;                           
                            case 8:
                                   if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn8.setText("X");
                                       }
                                     });
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn8.setText("O");
                                       }
                                     });
                                break;
                            case 9:
                                if(flag)
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn9.setText("X");
                                       }
                                     });
                                else
                                    Platform.runLater(new Runnable() {
                                  @Override public void run() {
                                    Btn9.setText("O");
                                       }
                                     });
                                break;
                            default:
                                break;
                        }
      
                if(flag)
                   flag = false;
                else
                   flag = true;
                try {
                    Thread.sleep(500);
                    } catch (InterruptedException ex) {
                Logger.getLogger(GameBordController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            
        }  
                }
           }).start(); 
    }

    public Button getBtn(int id) {
        Button btn;
        switch (id) {
            case 1:
                btn = Btn1;
                break;
            case 2:
                btn = Btn2;
                break;
            case 3:
                btn = Btn3;
                break;
            case 4:
                btn = Btn4;
                break;
            case 5:
                btn = Btn5;
                break;
            case 6:
                btn = Btn6;
                break;
            case 7:
                btn = Btn7;
                break;
            case 8:
                btn = Btn8;
                break;
            case 9:
                btn = Btn9;
                break;
            default:
                btn = Btn1;
                break;

        }
        return btn;
    }

//    private void parser(String moves) {
//
//        parsedMoves = moves.split("\\.");
//        for (String pars : parsedMoves) {
//            System.out.println("PARSED: " + pars);
//        }
//    }

    private void hold() {

    }

}