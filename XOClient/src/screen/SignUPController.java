package screen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import xoClientModel.signINBase;


public class SignUPController implements Initializable {

    public ActionEvent ec;
    @FXML
    private TextField pass_text;

    @FXML
    private Button signUPP_btn;

    @FXML
    private TextField name_text;
    @FXML
    private PasswordField passconf_text;
    @FXML
    private Label name_txt_msg;
    @FXML
    private Label pass_txt_msg;
    @FXML
    private Label passConf_txt_msg;
    String ip;
    Thread th;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void ip_value(String IP) {
        ip = IP;
    }

    @FXML
    private void btnCLICK(ActionEvent event) {

        boolean fn = isValidUsername(name_text.getText());
        boolean fp = isaValidpassword(pass_text.getText());
        boolean fpc = false;
        if (pass_text.getText().equals(passconf_text.getText())) {
            fpc = true;
        }

        if (fpc && fp && fn) {

            String name = name_text.getText();
            String pass = pass_text.getText();
            String str = "REG#" + name + "#" + pass;
            SignIN2Controller.ps.println(str);
            SignIN2Controller.ps.flush();

            th = new Thread(new Runnable() {
                public void run() {
                    try {
                        String msg = SignIN2Controller.dis.readLine();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (!msg.equals("Register Confirmed")) {

                                    Alert alert = new Alert(Alert.AlertType.ERROR, "This data is already exist.", ButtonType.OK);
                                    alert.getDialogPane().setMinHeight(Region.USE_COMPUTED_SIZE);
                                    alert.show();
                                } else {

                                    try {
                                        FXMLLoader loader = new FXMLLoader();
                                        loader.setLocation(getClass().getResource("/xoClientView/ENTER.fxml"));
                                        Parent viewParent = loader.load();
                                        Scene viewscene = new Scene(viewParent);
                                        ENTERController controller = loader.getController();
                                        controller.nPlayerName(name_text.getText());
                                        controller.nPlayerScore(0);
                                        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                        window.setScene(viewscene);
                                        window.show();
                                    } catch (IOException ex) {
                                        Logger.getLogger(SignIN2Controller.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                }
                            }
                        });
                    } catch (IOException ex) {
                        SignIN2Controller.whenServerOff();
                        SignIN2Controller.returnToMainPage(name_text);
                    }
                }
            });
            th.start();
        }

        if (!fn) {
            name_txt_msg.setText("Invalid Name");
            name_text.clear();
        } else {
            name_txt_msg.setText("");
        }
        if (!fp) {
            pass_txt_msg.setText("Invalid password");
            pass_text.clear();
        } else {
            pass_txt_msg.setText("");
        }
        if (fpc) {
            passConf_txt_msg.setText("Confirmed passowrd");
        } else {
            passConf_txt_msg.setText("Not confirmed passowrd");
            passconf_text.clear();
        }

    }

    @FXML
    private void back_click(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/xoClientView/signIN2.fxml"));
        Parent viewParent;
        try {
            viewParent = loader.load();
            Scene viewscene = new Scene(viewParent);
            SignIN2Controller controller = loader.getController();
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(viewscene);
            window.show();
        } catch (IOException ex) {
            Logger.getLogger(SignUPController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isValidUsername(String name) {
        String regex = "^[A-Za-z]\\w{2,29}$";
        Pattern p = Pattern.compile(regex);

        if (name == null) {
            return false;
        }
        if (name.length() >= 30) {
            return false;
        }
        Matcher m = p.matcher(name);

        return m.matches();
    }

    public static boolean isaValidpassword(String password) {
        if (!((password.length() >= 5)
                && (password.length() <= 29))) {
            return false;
        }
        if (password.contains(" ")) {
            return false;
        }
        if ((password.contains("#"))) {
            return false;
        }

        return true;
    }
}
