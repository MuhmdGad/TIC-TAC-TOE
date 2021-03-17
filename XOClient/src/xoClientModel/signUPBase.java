package xoClientModel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

public class signUPBase extends BorderPane {

    protected final Label label;
    protected final AnchorPane anchorPane;
    protected final AnchorPane anchorPane0;
    protected final TextField pass_text;
    protected final TextField email_text;
    protected final Button signUPP_btn;
    protected final TextField name_text;
    protected final Label label0;
    protected final Label label1;
    protected final Label label2;
    protected final Label label3;

    public signUPBase() {

        label = new Label();
        anchorPane = new AnchorPane();
        anchorPane0 = new AnchorPane();
        pass_text = new TextField();
        email_text = new TextField();
        signUPP_btn = new Button();
        name_text = new TextField();
        label0 = new Label();
        label1 = new Label();
        label2 = new Label();
        label3 = new Label();

        setMaxHeight(USE_PREF_SIZE);
        setMaxWidth(USE_PREF_SIZE);
        setMinHeight(USE_PREF_SIZE);
        setMinWidth(USE_PREF_SIZE);
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        setStyle("-fx-background-color: #9BD8BA;");

        BorderPane.setAlignment(label, javafx.geometry.Pos.CENTER);
        label.setPrefHeight(60.0);
        label.setPrefWidth(127.0);
        setTop(label);

        BorderPane.setAlignment(anchorPane, javafx.geometry.Pos.CENTER);
        anchorPane.setPrefHeight(200.0);
        anchorPane.setPrefWidth(200.0);

        anchorPane0.setLayoutX(38.0);
        anchorPane0.setLayoutY(46.0);
        anchorPane0.setPrefHeight(249.0);
        anchorPane0.setPrefWidth(524.0);

        pass_text.setLayoutX(147.0);
        pass_text.setLayoutY(77.0);
        pass_text.setPrefHeight(40.0);
        pass_text.setPrefWidth(354.0);

        email_text.setLayoutX(147.0);
        email_text.setLayoutY(131.0);
        email_text.setPrefHeight(40.0);
        email_text.setPrefWidth(354.0);

        signUPP_btn.setLayoutX(147.0);
        signUPP_btn.setLayoutY(195.0);
        signUPP_btn.setMnemonicParsing(false);
        signUPP_btn.setPrefHeight(40.0);
        signUPP_btn.setPrefWidth(354.0);
        signUPP_btn.setStyle("-fx-background-color: #3FAC76;");
        signUPP_btn.setText("Sign up");
        signUPP_btn.setFont(new Font("Cambria Math", 19.0));

        name_text.setLayoutX(147.0);
        name_text.setLayoutY(18.0);
        name_text.setPrefHeight(40.0);
        name_text.setPrefWidth(354.0);

        label0.setLayoutX(4.0);
        label0.setLayoutY(137.0);
        label0.setText("YOUR E-MAIL");
        label0.setFont(new Font("Cambria Math", 23.0));

        label1.setLayoutX(1.0);
        label1.setLayoutY(24.0);
        label1.setText("USER NAME");
        label1.setFont(new Font("Cambria Math", 25.0));

        label2.setLayoutX(4.0);
        label2.setLayoutY(83.0);
        label2.setText("PASSWORD");
        label2.setFont(new Font("Cambria Math", 25.0));

        label3.setLayoutX(210.0);
        label3.setLayoutY(-23.0);
        label3.setText("Sign up");
        label3.setTextFill(javafx.scene.paint.Color.WHITE);
        label3.setFont(new Font("Cambria Math", 41.0));
        setCenter(anchorPane);

        anchorPane0.getChildren().add(pass_text);
        anchorPane0.getChildren().add(email_text);
        anchorPane0.getChildren().add(signUPP_btn);
        anchorPane0.getChildren().add(name_text);
        anchorPane0.getChildren().add(label0);
        anchorPane0.getChildren().add(label1);
        anchorPane0.getChildren().add(label2);
        anchorPane.getChildren().add(anchorPane0);
        anchorPane.getChildren().add(label3);

    }
}
