/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver.view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import xoserver.model.MainServer;

/**
 *
 * @author Taha 
 */
public class XOServer extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = new ServerGUI();
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("TIC TAC TOE SERVER");
        stage.setResizable(false);
        stage.centerOnScreen();     
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                try {
                    ServerGUI.closingEverything();
                } catch (IOException ex) {
                    Logger.getLogger(XOServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                stage.close();
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
