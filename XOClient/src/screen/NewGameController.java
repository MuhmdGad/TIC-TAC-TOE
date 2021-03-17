package screen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class NewGameController implements Initializable {

    @FXML
    private Button singleplay_btn;
    @FXML
    private Button multLOCALi_btn;
    @FXML
    private Button Record_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            File file = new File("localGameRecord.txt");
            File file2 = new File("singleGameRecord.txt");
            file.createNewFile();
            file2.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(NewGameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void back_click(ActionEvent event) throws IOException {
         
          FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("/xoClientView/signIN.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          SignINController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }
     @FXML
    private void HandleSinglePlayer(ActionEvent event) throws IOException
    {
          FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("/xoClientView/SingleUserName.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          SingleUserNameController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }
    @FXML
    private void HandleLocalPlayers(ActionEvent event) throws IOException
    {
          FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("/xoClientView/LocalUserNames.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          LocalUserNamesController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }
     @FXML
    private void RecordingAction(ActionEvent event) throws IOException {
       
         FXMLLoader loader =new FXMLLoader();
          loader.setLocation(getClass().getResource("/xoClientView/record.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          RecordController controller =loader.getController();
          controller.flag_value("network");
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
    }
    
}
