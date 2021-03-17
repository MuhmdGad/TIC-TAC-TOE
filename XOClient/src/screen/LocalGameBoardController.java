/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.io.BufferedWriter;
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


public class LocalGameBoardController implements Initializable {
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
    @FXML
    private ImageView label_img;
    public ArrayList<Integer> gameMoves = new ArrayList<>();
    private String startGame ;
    int xoWinner=-2;
    boolean yes = false;
    int buttonUsed [] = {0,0,0,0,0,0,0,0,0};
    int[][] winningPositions = {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
        {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
        {0, 4, 8}, {2, 4, 6}
    };
     int gameState[] = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
	Alert dlg = new Alert(Alert.AlertType.CONFIRMATION);
	//dlg.setTitle("Move Directory");
	dlg.setHeaderText("Record Game");
	dlg.setContentText("Do you want record this game ?");
	dlg.getButtonTypes().clear();
	dlg.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
	dlg.showAndWait();
	yes = dlg.getResult() == ButtonType.YES;
       
    }
    
     public void setText(String text1 , String text2 , String text3 , String text4 )
    {
        player1.setText(text1);
        player2.setText(text2);
        player1Symbol.setText(text3);
        player2Symbol.setText(text4);
        startGame =text3;
  
    }
 

    @FXML
    private void Done_btn(ActionEvent event) {
        try {
            mediaPlayer.stop();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/newGame.fxml"));
            Parent viewparent = loader.load();
            Scene viewscene = new Scene(viewparent);
            NewGameController controller = loader.getController();
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(LocalGameBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void Btn_action(ActionEvent event) {
       
         Platform.runLater(() -> {
        Button btn=(Button)event.getSource();
        String[] ID = btn.getId().split("n");
        int number = Integer.parseInt(ID[1]);
        if(buttonUsed[number-1]==0){
             buttonUsed[number-1]=1; 
            if(startGame.equals("X"))
            {
                btn.setText(startGame);
                btn.setDisable(true);
                gameState[number-1]=0; 
            }
            else
            {
                btn.setText(startGame);
                btn.setDisable(true);
                gameState[number-1]=1;
            }
            gameMoves.add(number);
        choese();
        WinnerGame();
        }
         });
   
       
    }
    
    public void WinnerGame() {
        
        boolean flag = true;
        for (int[] win : winningPositions) {
            if (gameState[win[0]] == gameState[win[1]] && gameState[win[1]] == gameState[win[2]] && gameState[win[0]] != 2) {
                flag = false;
                if (gameState[win[0]]==0){ xoWinner= 0;}
                else {xoWinner= 1;}
                disable();
                winnerName();
                set_color();
                VidioShow("/screen/winningVideo.mp4");
                break;
            }
            
            
        }
        if (isDraw() && flag) {
            winner_loser_txt.setText("That's a Draw"); 
            VidioShow("/screen/drawVideo.mp4");
            disable();
            
        }
    }
    
     public boolean isDraw() {
        boolean draw = true;
        for (int btn : buttonUsed) {
            if (btn != 1) {
                draw = false;
            }
        }
        return draw;
    }
    
    private void choese (){
    
        if (startGame.equals("X"))
        {
            startGame ="O";
        }
        else 
        {
            startGame ="X";
        }  
    }
    
     public void winnerName(){
        String Data ="" ;
        for (int i = 0; i < gameMoves.size();i++ )
        {
           Data += gameMoves.get(i) + "#";
           
        }
        System.out.println(Data);
         switch (xoWinner) {
            case 0:{
                
                if(player1Symbol.getText().equals("X")){
                    
                    winner_loser_txt.setText(player1.getText()+" is winner");
                    System.out.println(player1.getText());
                    Data += player1Symbol.getText()+"&"+ player1.getText()+ "@" + "X@"+player2.getText()+"@O@";
                }
                else{
                    winner_loser_txt.setText(player2.getText()+" is winner");
                    System.out.println(player2.getText());
                    Data += player1Symbol.getText()+"&"+ player2.getText() + "@" + "X@"+player1.getText()+"@O@";
                }
               
                break;
            }
            case 1:{
                
                if(player1Symbol.getText().equals("O")){
                    winner_loser_txt.setText(player1.getText()+" is winner");
                    System.out.println(player1.getText());
                    Data +=player1Symbol.getText()+"&"+  player1.getText()+ "@" + "O@"+player2.getText()+"@X@";
                }
                else{
                    winner_loser_txt.setText(player2.getText()+" is winner");
                    System.out.println(player2.getText());
                    Data +=player1Symbol.getText()+"&"+ player2.getText() + "@" + "O@"+player1.getText()+"@X@";
                }
                break;
            }
            case -1: {
                Data += player1Symbol.getText()+"&"+ player1.getText() + "@"+ player1Symbol.getText() +"@"+player2.getText()+"@"+ player2Symbol.getText()+"@";
                break;
            }
            
        }
         System.out.println(Data);
         if(yes){
           BufferedWriter out ;
           try {
              out = new BufferedWriter(
              new FileWriter("localGameRecord.txt", true));
              out.write(Data + "!");
              out.close();  
            } catch (IOException ex) {
                  Logger.getLogger(LocalGameBoardController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            }
         
    }
      public void set_color(){
          if(Btn1.getText().equals(Btn2.getText())&&Btn2.getText().equals(Btn3.getText()) && !Btn1.getText().isEmpty())
          {
            color(Btn1,Btn2,Btn3);  
          }
          else if(Btn4.getText().equals(Btn5.getText())&&Btn5.getText().equals(Btn6.getText())&& !Btn4.getText().isEmpty())
          {
              color(Btn4,Btn5,Btn6);
          }
          else if(Btn7.getText().equals(Btn8.getText())&&Btn8.getText().equals(Btn9.getText())&& !Btn7.getText().isEmpty())
          {
              color(Btn7,Btn8,Btn9);
          }
          else if(Btn1.getText().equals(Btn4.getText())&&Btn4.getText().equals(Btn7.getText())&& !Btn1.getText().isEmpty())
          {
              color(Btn1,Btn4,Btn7);
          }
          else if(Btn2.getText().equals(Btn5.getText())&&Btn5.getText().equals(Btn8.getText())&& !Btn2.getText().isEmpty())
          {
              color(Btn2,Btn5,Btn8);
          }
          else if(Btn3.getText().equals(Btn6.getText())&&Btn6.getText().equals(Btn9.getText())&& !Btn3.getText().isEmpty())
          {
              color(Btn3,Btn6,Btn9);
          }
           else if(Btn1.getText().equals(Btn5.getText())&&Btn5.getText().equals(Btn9.getText())&& !Btn1.getText().isEmpty())
          {
              color(Btn1,Btn5,Btn9);
          }
           else if(Btn3.getText().equals(Btn5.getText())&&Btn5.getText().equals(Btn7.getText())&& !Btn3.getText().isEmpty())
          {
              color(Btn3,Btn5,Btn7);
          }
      }
     
    public void VidioShow(String path){
         
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
     public void disable ()
        {
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
    public void color(Button btn1, Button btn2  , Button btn3)
       {
            btn1.setStyle("-fx-background-color:  white");
            btn2.setStyle("-fx-background-color:  white");
            btn3.setStyle("-fx-background-color:  white");
   
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