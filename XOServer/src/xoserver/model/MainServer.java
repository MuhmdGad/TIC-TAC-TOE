/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver.model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Taha 
 */
public class MainServer extends Thread {

    public static ServerSocket mainSocket;
    private DatabaseConnection dbConnection;
    private static MainServer mainServerObject;

    private MainServer() {
        try {
            mainSocket = new ServerSocket(5050);
            dbConnection = DatabaseConnection.getDatabaseInstance();
        } catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static MainServer getInstance() {
        if (mainServerObject == null) {
            mainServerObject = new MainServer();
        }
        return mainServerObject;
    }

    public static void deleteInstance() {
        mainServerObject = null;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket s = mainSocket.accept();
                new GameHandler(s);
            } catch (IOException ex) {
                Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void stopClients() throws IOException {  //stops clients when closing server
        for (GameHandler s : GameHandler.clientVector) {
            s.clientSocket.close();
            s.ps.close();
            s.dis.close();
            s.stop();
        }
        GameHandler.clientVector.removeAllElements();
    }
}
