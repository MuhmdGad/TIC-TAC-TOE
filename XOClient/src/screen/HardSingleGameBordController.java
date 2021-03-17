/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.io.BufferedWriter;
import xoClientModel.HardLevel;
import xoClientModel.Evaluation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Eslam
 */
public class HardSingleGameBordController implements Initializable {

   @FXML
    private Label player1 , player2, player1Symbol ,player2Symbol;

@FXML
    private Button Btn1,Btn2,Btn3,Btn4,Btn5,Btn6,Btn7,Btn8,Btn9;
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
    @FXML
    private ImageView label_img;
    
    String flag ;
    
     boolean yes;
    int moveNum = 0;
    HardLevel.Move bestMove;
    String path;
    Button[][] board = new Button[3][3];
     public ArrayList<Integer> gameMoves = new ArrayList<>();  
   
      public void setText(String text1 , String text2 , String text3 , String text4 )
    {
        player1.setText(text1);
        player2.setText(text2);
        player1Symbol.setText(text3);
        player2Symbol.setText(text4);
        
        HardLevel.player = player2Symbol.getText();
        HardLevel.opponent = player1Symbol.getText();
       
         func();
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
   
     
        Alert dlg = new Alert(Alert.AlertType.CONFIRMATION);
	dlg.setHeaderText("Record Game");
	dlg.setContentText("Do you want record this game ?");
	dlg.getButtonTypes().clear();
	dlg.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
	dlg.showAndWait();
	yes = dlg.getResult() == ButtonType.YES;
        
        board [0][0] = Btn1;
        board [0][1] = Btn2;
        board [0][2] = Btn3;
        board [1][0] = Btn4;
        board [1][1] = Btn5;
        board [1][2] = Btn6;
        board [2][0] = Btn7;
        board [2][1] = Btn8;
        board [2][2] = Btn9;
      
    }
    public void func(){
        for (Button[] btns : board) {
            for (Button btn : btns) {

                btn.addEventHandler(ActionEvent.ACTION, (ActionEvent event) -> {
                    Button btnA = (Button) event.getSource();
                    String[] ID = btnA.getId().split("n");
                    int number = Integer.parseInt(ID[1]);
                    gameMoves.add(number);
                    btn.setDisable(true);
                    btn.setText(player1Symbol.getText());
                    System.out.println("xxx");
                    btn.setMouseTransparent(true);
                    if (moveNum + 1 < 9) {
                        bestMove = HardLevel.findBestMove(board);
                        board[bestMove.row][bestMove.col].setText(player2Symbol.getText());
                        board[bestMove.row][bestMove.col].setMouseTransparent(true);
                        if(bestMove.row == 0)
                        {
                            switch(bestMove.col){
                                case 0:{
                                    gameMoves.add(1);
                                    Btn1.setDisable(true);
                                    break;
                                }
                                case 1:{
                                    gameMoves.add(2);
                                     Btn2.setDisable(true);
                                    break;
                                }
                                case 2:{
                                    gameMoves.add(3);
                                    Btn3.setDisable(true);
                                    break;
                                }
                                    
                            }
                        }
                       else if(bestMove.row == 1)
                        {
                            switch(bestMove.col){
                                case 0:{
                                    gameMoves.add(4);
                                    Btn4.setDisable(true);
                                    break;
                                }
                                case 1:{
                                    gameMoves.add(5);
                                    Btn5.setDisable(true);
                                    break;
                                }
                                case 2:{
                                    gameMoves.add(6);
                                    Btn6.setDisable(true);
                                    break;
                                }
                                    
                            }
                        }
                        else if(bestMove.row == 2)
                        {
                            switch(bestMove.col){
                                case 0:{
                                    gameMoves.add(7);
                                    Btn7.setDisable(true);
                                    break;
                                }
                                case 1:{
                                    gameMoves.add(8);
                                    Btn8.setDisable(true);
                                    break;
                                }
                                case 2:{
                                    gameMoves.add(9);
                                    Btn9.setDisable(true);
                                    break;
                                }
                                    
                            }
                        }
                    }
                    
                    moveNum += 2;
                    if (moveNum >= 5) {
                                 
                        int result = Evaluation.evaluate(board);
                        if(result == 10 || result == -10 || HardLevel.isMoveLeft(board) == false)
                        {
                             String Data ="" ;
                          for (int i = 0; i < gameMoves.size();i++ )
                            {
                              Data += gameMoves.get(i) + "#";
                            } 
                          if (result == 10) {
                            System.out.println("You lost :(");
                            winner_loser_txt.setText(player1.getText()+ " is loser");
                            Data += player1Symbol.getText()+"&"+ player2.getText()+ "@" + player2Symbol.getText()+"@"+player1.getText()+"@"+player1Symbol.getText()+"@";
                            set_color();
                            path = "/screen/lossingVideo.mp4";//loser
                            show_video(path);
                          } else if (result == -10) {
                            System.out.println("You won ^^");
                            winner_loser_txt.setText(player1.getText() + "is winner");//winner
                            Data += player1Symbol.getText()+"&"+ player1.getText()+ "@" + player1Symbol.getText()+"@"+player2.getText()+"@"+player2Symbol.getText()+"@";
                            set_color();
                            path = "/screen/winningVideo.mp4";
                            show_video(path);
                          } else if (HardLevel.isMoveLeft(board) == false) {
                            System.out.println("No One Wins !");
                            winner_loser_txt.setText("That's a Draw");//draw
                            Data += player1Symbol.getText()+"&"+ player1.getText()+ "@" + player1Symbol.getText()+"@"+player2.getText()+"@"+player2Symbol.getText()+"@";
                            path = "/screen/drawVideo.mp4";
                            show_video(path);
                          }
                          if(yes){
                            BufferedWriter out ;
                            try {
                              out = new BufferedWriter(
                              new FileWriter("singleGameRecord.txt", true));
                              out.write(Data + "!");
                              out.close();  
                            } catch (IOException ex) {
                              Logger.getLogger(LocalGameBoardController.class.getName()).log(Level.SEVERE, null, ex);
                            }
            
                          }
                        }
                    }
                });
            }
        }
    } 
    @FXML
    private void Done_btn(ActionEvent event) throws IOException
    {
             mediaPlayer.stop();
             FXMLLoader loader =new FXMLLoader();
             loader.setLocation(getClass().getResource("/xoClientView/newGame.fxml"));
             Parent viewParent =loader.load();
             Scene viewscene =new Scene (viewParent);
             NewGameController controller =loader.getController(); 
             Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
             window.setScene(viewscene);
             window.show();
    }
  

        
    public void set_color(){
        if(Evaluation.winRow == 123 )
          {
            board[0][Evaluation.winCol].setStyle("-fx-background-color: white");
            board[1][Evaluation.winCol].setStyle("-fx-background-color: white");
            board[2][Evaluation.winCol].setStyle("-fx-background-color: white");
          }
        else if(Evaluation.winCol == 123)
          {
            board[Evaluation.winRow][0].setStyle("-fx-background-color: white");
            board[Evaluation.winRow][1].setStyle("-fx-background-color: white");
            board[Evaluation.winRow][2].setStyle("-fx-background-color: white"); 
          }
        else if(Evaluation.winRow == 20)
          {
            board[0][0].setStyle("-fx-background-color: white");
            board[1][1].setStyle("-fx-background-color: white");
            board[2][2].setStyle("-fx-background-color: white");
          }
        else if(Evaluation.winRow == 40)
          {
            board[0][2].setStyle("-fx-background-color: white");
            board[1][1].setStyle("-fx-background-color: white");
            board[2][0].setStyle("-fx-background-color: white");
          }
                           
    }
                    
    public void show_video(String path)
    {
        new Thread(new Runnable() {
            public void run(){
                try {
                  Thread.sleep(1000);
                }catch (InterruptedException ex) {
                Logger.getLogger(GameBordController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Platform.runLater(new Runnable() {
                    @Override public void run() {
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