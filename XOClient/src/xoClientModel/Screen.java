package xoClientModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import screen.ENTERController;
import screen.SignIN2Controller;
import screen.FreeOnlinePlayersController;
import screen.NetworkGameBoardController;
import screen.NetworkRecordController;

public class Screen extends Application {

    public static Boolean isSplashloaded = false;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/xoClientView/signIN.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(710);
        stage.setHeight(550);
        stage.setResizable(false);
        stage.setTitle("TIC TAC TOE");
        stage.centerOnScreen();
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                if (ENTERController.isOnline) {
                    try {
                        System.out.println("Stage is online closing");
                        String logoutMessage = "SOUT#" + ENTERController.Name;
                        SignIN2Controller.ps.println(logoutMessage);
                        SignIN2Controller.dis.close();
                        SignIN2Controller.ps.close();
                        SignIN2Controller.sClient.close();
                        if (FreeOnlinePlayersController.isReplyThreadOn) {
                            FreeOnlinePlayersController.replyThread.stop();
                        }
                        if (FreeOnlinePlayersController.isRequestThreadOn) {
                            FreeOnlinePlayersController.requestThread.stop();
                        }
                        if (NetworkGameBoardController.isPlayThreadOn) {
                            NetworkGameBoardController.th.stop();
                        }
                        if (ENTERController.isReplyThreadEnterOn) {
                            ENTERController.replyThreadEnter.stop();
                        }
                        if (NetworkRecordController.isThreadOn) {
                            NetworkRecordController.getRecThread.stop();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Screen.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                stage.close();
                Platform.exit();
            }
        });
    }

    public static void main(String[] args) {

        launch(args);

    }

}
