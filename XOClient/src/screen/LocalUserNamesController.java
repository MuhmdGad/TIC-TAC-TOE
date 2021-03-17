package screen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


public class LocalUserNamesController implements Initializable {

    @FXML
    private TextField player1 , player2;
    @FXML
    private RadioButton x ,o;
    @FXML
    private ToggleGroup rad_x;
    @FXML
    private Label symbol2;
    

     @FXML
    private void handleLocalPlayersAction(ActionEvent event) throws IOException {
       
        
     String symbole1 = " ";
     String symbole2 = " ";
     
     if (x.isSelected())
        {
             symbole1 = "X";
             symbole2 = "O";
        }
     else if(o.isSelected())
        {
             symbole1 = "O";
             symbole2 = "X";
        }
     if((player1.getText().isEmpty() || player1.getText().contains(" "))||( player2.getText().isEmpty() || player2.getText().contains(" ")))
   {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "yours names is required without spaces.", ButtonType.OK);
                                            alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
                                            alert.show();
   }
     else{
        
       FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/xoClientView/LocalGameBoard.fxml"));
        Parent viewparent = loader.load();
        Scene viewscene = new Scene(viewparent);
        LocalGameBoardController controller = loader.getController();
        controller.setText(player1.getText() ,player2.getText() ,symbole1 ,symbole2);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(viewscene);
        window.show();
     }
    }
    @FXML
    private void back_btn(ActionEvent event) throws IOException{
      
        FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("/xoClientView/newGame.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          NewGameController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       symbol2.setText("Your Symbole is O");
    }    
     @FXML
    private void radioAction(ActionEvent event) {
        
        
        if (x.isSelected())
        {
            symbol2.setText("Your Symbole is O");
        }
     else if(o.isSelected())
        {
            symbol2.setText("Your Symbole is X"); 
        }
    }
}