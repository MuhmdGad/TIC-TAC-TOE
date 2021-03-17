/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screen;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import screen.ENTERController;
import screen.FreeOnlinePlayersController;
import screen.NewGameController;
import screen.SignIN2Controller;
import screen.SignINController;
import xoClientModel.ENTERBase;


public class NetworkRecordController implements Initializable {

    @FXML
    private Button Btn1;
    @FXML
    private Button Btn2;
    @FXML
    private Button Btn3;
    @FXML
    private Button Btn4;
    @FXML
    private Button Btn5;
    public String name;
    public int score;
    public static Thread getRecThread;
    String recievedMsg;
    String[] checkMsg;
    static String[] lineParsedMsg;
    String[] dividedLineMsg;
    static String[] recordParsedMsg;
    boolean firstItemFlag = true;
    String[] afterCheckMsg;
    public static boolean isThreadOn = false;
    //int check=0;

    /*public static Socket recordPageSocket;
    public static DataInputStream disRec;
    public static PrintStream psRec;*/
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*try {
            /*recordPageSocket=new Socket(SignINController.serverIP,5008);
            disRec=new DataInputStream(recordPageSocket.getInputStream());
            psRec=new PrintStream(recordPageSocket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(NetworkRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        //check=0;
        ENTERController.replyThreadEnter.stop();
        if (FreeOnlinePlayersController.isRequestThreadOn) {
            FreeOnlinePlayersController.requestThread.stop();
        }
        getRec();
        getRecThread.start();
    }

    @FXML
    private void Btn_click(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String[] ID = btn.getId().split("n");
        int number = Integer.parseInt(ID[1]);
        if (((lineParsedMsg.length - (number - 1)) > 0) && lineParsedMsg != null) {  //check==1 && 
            lineDividerParser(lineParsedMsg[number - 1]);
            try {
                recordParser(dividedLineMsg[2]);
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/xoClientView/OnlineRecordBoard.fxml"));
                Parent viewParent = loader.load();
                Scene viewscene = new Scene(viewParent);
                OnlineRecordBoardController controller = loader.getController();
                controller.setText(dividedLineMsg[0], dividedLineMsg[1], "X", "O");
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(viewscene);
                window.show();
                //check=1;
            } catch (IOException ex) {
                Logger.getLogger(NetworkRecordController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                getRecThread.stop();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Empty record");
            alert.setContentText("no recording here");
            alert.show();
        }
    }

    @FXML
    private void back_click(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/xoClientView/ENTER.fxml"));
            Parent viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            screen.ENTERController controller = loader.getController();
            controller.nPlayerName(ENTERController.Name);
            controller.nPlayerScore(ENTERController.playerScore);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
            getRecThread.stop();
        } catch (IOException ex) {
            Logger.getLogger(NetworkRecordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getRec() {
        getRecThread = new Thread(new Runnable() {
            @Override
            public void run() {
                isThreadOn = true;
                SignIN2Controller.ps.println("GETREC#" + ENTERController.Name);
                while (true) {
                    try {
                        recievedMsg = SignIN2Controller.dis.readLine();
                        System.out.println("RECIEVEDMSG: " + recievedMsg);
                        checkParser(recievedMsg);
                        if (checkMsg.length > 1) {
                            if ((checkMsg[0].equals("TREC") || checkMsg[0].equals("ETREC") || checkMsg[0].equals("GETREC")) && checkMsg[1].equals(ENTERController.Name)) {
                                lineParser(afterCheckMsg[1]);
                                System.out.println("line parser size: " + lineParsedMsg.length);
                                //check=1;
                                //recordParser(dividedLineMsg[2]);
                                //lineDividerParser(lineParsedMsg[2]);
                                //recordParser(dividedLineMsg[2]);
                            }
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(NetworkRecordController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
    }

    public void checkParser(String msg) {
        if (msg != null) {
            afterCheckMsg = msg.split("\\@");
            checkMsg = afterCheckMsg[0].split("\\#");
        }
    }

    public void lineParser(String msg) {
        if (msg != null) {
            lineParsedMsg = msg.split("!");
        }
    }

    public void lineDividerParser(String msg) {
        dividedLineMsg = msg.split("\\#");
        for (String s : dividedLineMsg) {
            System.out.println(s);
        }
    }

    public void recordParser(String msg) {
        if (msg != null) {
            recordParsedMsg = msg.split("\\.");
        }
    }

}
