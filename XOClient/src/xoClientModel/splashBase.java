package xoClientModel;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class splashBase extends AnchorPane {

    protected final Label label;
    protected final Label label0;

    public splashBase() {

        label = new Label();
        label0 = new Label();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        setStyle("-fx-background-color: #8c062c;");

        label.setLayoutX(149.0);
        label.setLayoutY(81.0);
        label.setPrefHeight(78.0);
        label.setPrefWidth(303.0);
        label.setText("TIC TAC TOE");
        label.setTextFill(javafx.scene.paint.Color.WHITE);
        label.setFont(new Font("Cambria Math", 53.0));

        label0.setLayoutX(257.0);
        label0.setLayoutY(297.0);
        label0.setPrefHeight(24.0);
        label0.setPrefWidth(88.0);
        label0.setText("Loading...");
        label0.setTextFill(javafx.scene.paint.Color.WHITE);
        label0.setFont(new Font("Cambria Math", 20.0));

        getChildren().add(label);
        getChildren().add(label0);

    }
}
