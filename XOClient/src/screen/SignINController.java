package screen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.naming.spi.DirStateFactory;
import javax.swing.JOptionPane;
import xoClientModel.Screen;


public class SignINController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Button gust_click;
    @FXML
    private Button network_btn ,guest_btn;
    public static String serverIP;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!Screen.isSplashloaded) {
            loadSplashScreen();
        }

       Image img = new Image("Style/network.PNG");
        ImageView view = new ImageView(img);
        view.setFitHeight(130);
        view.setFitWidth(130);
        network_btn.setGraphic(view);
       
 
       Image img2 = new Image("Style/guest.jpg");
       ImageView view2 = new ImageView(img2);
       view2.setFitHeight(130);
       view2.setFitWidth(130);
       guest_btn.setGraphic(view2);
       
 
    }    

    private void loadSplashScreen() {

        try {
            Screen.isSplashloaded = true;
            AnchorPane pane = FXMLLoader.load(getClass().getResource("/xoClientView/splash.fxml"));
            root.getChildren().setAll(pane);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), pane);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.setCycleCount(1);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), pane);
            fadeOut.setFromValue(1);
            fadeOut.setToValue(0);
            fadeOut.setCycleCount(1);

            fadeIn.play();
            fadeIn.setOnFinished((e) -> {
                fadeOut.play();
            });

            fadeOut.setOnFinished((e) -> {
                try {
                    AnchorPane parentConted = FXMLLoader.load(getClass().getResource("/xoClientView/signIN.fxml"));
                    root.getChildren().setAll(parentConted);
                } catch (IOException ex) {
                    Logger.getLogger(SignINController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

        } catch (IOException ex) {
            Logger.getLogger(SignINController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void guest_click(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/xoClientView/newGame.fxml"));
        Parent viewParent = loader.load();
        Scene viewscene = new Scene(viewParent);
        NewGameController controller = loader.getController();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(viewscene);
        window.show();
    }

    @FXML
    private void network_click(ActionEvent event) {
        String ip = "192.168.247.128";
        boolean ex_flag = true;
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Dialog");
            dialog.setContentText("Please enter the server IP address :");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
            }
            ip = result.get();
        } catch (NoSuchElementException e) {
            ex_flag = false;
        }

        boolean flag = isIpv4(ip);
        if (flag) {
            SocketAddress socketAddress = new InetSocketAddress(ip, 5050);
            serverIP = ip;
            Socket socket = new Socket();

            try {
                socket.connect(socketAddress, 500);
                socket.close();

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/xoClientView/signIN2.fxml"));
                Parent viewParent = loader.load();
                Scene viewscene = new Scene(viewParent);
                SignIN2Controller controller = loader.getController();
                controller.ip_value(ip);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(viewscene);
                window.show();

            } catch (SocketTimeoutException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "This server is not found.", ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
                alert.show();
            } catch (IOException ex) {
            }

        } else if (ex_flag) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, Wrong IP");

            alert.showAndWait();

        }

    }

    public boolean isIpv4(String ipAddress) {
        if (ipAddress == null) {
            return false;
        }
        String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern pattern = Pattern.compile(ip);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

}
