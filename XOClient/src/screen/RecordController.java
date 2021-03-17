package screen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.stage.Stage;


public class RecordController implements Initializable {

    @FXML
    private Button Btn1,Btn2,Btn3,Btn4,Btn5;
    @FXML
    private Button BTn1,BTn2,BTn3,BTn4,BTn5;
    
    
    String flag;
    public String [] lines, Lines2;
    BufferedReader br;
    BufferedReader br2;
    FileReader fr; 
    String L ,L2;
    /**
     * Initializes the controller class.
     */
    public void flag_value(String FLAG)
    {
        flag = FLAG;
    }
    public String[] parsing_lines(String requestMessage){
        String[] arrOfStr = requestMessage.split("!"); 
       return arrOfStr;
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         FileReader fr;
         FileReader fr2;
    try {
        fr = new FileReader("localGameRecord.txt");
        BufferedReader br=new BufferedReader(fr); 
        L = br.readLine();
        fr2 = new FileReader("singleGameRecord.txt");
        BufferedReader br2 =new BufferedReader(fr2); 
        L2 = br2.readLine();
    } catch (FileNotFoundException ex) {
        Logger.getLogger(GameBordController.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(GameBordController.class.getName()).log(Level.SEVERE, null, ex);
    }
    if( L != null)
    {
         lines = parsing_lines(L);
         switch (lines.length){
             case 1 :
                 Btn1.setVisible(true);
                 break;
            case 2 :{
                 Btn1.setVisible(true);
                 Btn2.setVisible(true);
                 break;
            }
            case 3 :{
                 Btn1.setVisible(true);
                 Btn2.setVisible(true);
                 Btn3.setVisible(true);
                 break;
            }
            case 4 :{
                 Btn1.setVisible(true);
                 Btn2.setVisible(true);
                 Btn3.setVisible(true);
                 Btn4.setVisible(true);
                 break;
            }
            case 5 :{
                 Btn1.setVisible(true);
                 Btn2.setVisible(true);
                 Btn3.setVisible(true);
                 Btn4.setVisible(true);
                 Btn5.setVisible(true);
                 break; 
            }
            default:{
                 Btn1.setVisible(true);
                 Btn2.setVisible(true);
                 Btn3.setVisible(true);
                 Btn4.setVisible(true);
                 Btn5.setVisible(true);
                 break; 
            }
         }
    }
     if( L2 != null)
    {
         Lines2 = parsing_lines(L2);
         switch (Lines2.length){
             
             case 1:
                 BTn1.setVisible(true);
                 
                 break;
            case 2:{
                
                 BTn1.setVisible(true);
                 BTn2.setVisible(true);
                 break;
            }
            case 3:{
                 BTn1.setVisible(true);
                 BTn2.setVisible(true);
                 BTn3.setVisible(true);
                 break;
            }
            case 4:{
                 BTn1.setVisible(true);
                 BTn2.setVisible(true);
                 BTn3.setVisible(true);
                 BTn4.setVisible(true);
                 break;
            }
            case 5:{
                 BTn1.setVisible(true);
                 BTn2.setVisible(true);
                 BTn3.setVisible(true);
                 BTn4.setVisible(true);
                 BTn5.setVisible(true);
                 break; 
            }
            default:{
                 BTn1.setVisible(true);
                 BTn2.setVisible(true);
                 BTn3.setVisible(true);
                 BTn4.setVisible(true);
                 BTn5.setVisible(true);
                 break; 
            }
                
                
         }
    }

       
    } 
 
    
    @FXML
    private void Btn_click(ActionEvent event) {
            boolean flag_switch = true;
            String line = "";
            Button btn = (Button) event.getSource();
            String[] ID = btn.getId().split("n");
            int number = Integer.parseInt(ID[1]);
            System.out.println(ID);
            if(ID[0].equals("Bt"))
            {
                line = L;
            }
            else
            {
               line = L2; 
            }
        try {
        if(line == null)
            {
               Alert alert = new Alert(Alert.AlertType.INFORMATION, "No game recording available.", ButtonType.OK);
                          alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
                          alert.show();  
            }
        else {
            lines = parsing_lines(line);
            FXMLLoader loader =new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/GameBord.fxml"));
            Parent viewParent =loader.load();
            Scene viewscene =new Scene (viewParent);
            GameBordController controller =loader.getController();
            if((lines.length - (number - 1)) > 0){
                btn.setVisible(true);
                controller.GameRecord(lines[lines.length - number]);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(viewscene);
                window.show();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No game recording available.", ButtonType.OK);
                          alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
                          alert.show();
                          flag_switch = false;
            }
        }
          
        } catch (IOException ex) {
            Logger.getLogger(RecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
       }
    

    @FXML
    private void back_click(ActionEvent event) throws IOException {
          
          FXMLLoader loader =new FXMLLoader();
       if (flag == "guest"){
          loader.setLocation(getClass().getResource("/xoClientView/ENTER.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          ENTERController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
       }else{
           
          loader.setLocation(getClass().getResource("/xoClientView/newGame.fxml"));
          Parent viewParent =loader.load();
          Scene viewscene =new Scene (viewParent);
          NewGameController controller =loader.getController();
          Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
          window.setScene(viewscene);
          window.show();
          
          }
    }
}