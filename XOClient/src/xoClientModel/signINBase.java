package xoClientModel;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class signINBase extends AnchorPane {

    protected final AnchorPane anchorPane;
    protected final TextField userName_txt;
    protected final TextField password_txt;
    protected final Button signIN_btn;
    protected final Label label;
    protected final Button signUP_btn;
    protected final Label label0;

    public signINBase() {

        anchorPane = new AnchorPane();
        userName_txt = new TextField();
        password_txt = new TextField();
        signIN_btn = new Button();
        label = new Label();
        signUP_btn = new Button();
        label0 = new Label();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        setStyle("-fx-background-color: #9BD8BA;");

        anchorPane.setLayoutX(91.0);
        anchorPane.setLayoutY(74.0);
        anchorPane.setPrefHeight(299.0);
        anchorPane.setPrefWidth(419.0);

        userName_txt.setLayoutX(52.0);
        userName_txt.setLayoutY(51.0);
        userName_txt.setPrefHeight(40.0);
        userName_txt.setPrefWidth(315.0);
        userName_txt.setPromptText("User name");

        password_txt.setLayoutX(52.0);
        password_txt.setLayoutY(110.0);
        password_txt.setPrefHeight(40.0);
        password_txt.setPrefWidth(315.0);
        password_txt.setPromptText("password");

        signIN_btn.setLayoutX(52.0);
        signIN_btn.setLayoutY(173.0);
        signIN_btn.setMnemonicParsing(false);
        signIN_btn.setPrefHeight(40.0);
        signIN_btn.setPrefWidth(315.0);
        signIN_btn.setStyle("-fx-background-color: #3FAC76;");
        signIN_btn.setText("Sign in");
        signIN_btn.setFont(new Font("Cambria Math", 19.0));

        label.setLayoutX(52.0);
        label.setLayoutY(238.0);
        label.setPrefHeight(40.0);
        label.setPrefWidth(192.0);
        label.setText("Don’t have an account?");
        label.setFont(new Font("Cambria Math", 15.0));

        signUP_btn.setLayoutX(235.0);
        signUP_btn.setLayoutY(238.0);
        signUP_btn.setMnemonicParsing(false);
        signUP_btn.setPrefHeight(40.0);
        signUP_btn.setPrefWidth(118.0);
        signUP_btn.setStyle("-fx-background-color: #9BD8BA;");
        signUP_btn.setText("Sign up");
        signUP_btn.setTextFill(javafx.scene.paint.Color.valueOf("#176b40"));
        signUP_btn.setFont(new Font("Cambria Math", 22.0));

        label0.setLayoutX(234.0);
        label0.setLayoutY(35.0);
        label0.setText("Sign in");
        label0.setTextFill(javafx.scene.paint.Color.WHITE);
        label0.setFont(new Font("Cambria Math", 41.0));

        anchorPane.getChildren().add(userName_txt);
        anchorPane.getChildren().add(password_txt);
        anchorPane.getChildren().add(signIN_btn);
        anchorPane.getChildren().add(label);
        anchorPane.getChildren().add(signUP_btn);
        getChildren().add(anchorPane);
        getChildren().add(label0);

    }
}
