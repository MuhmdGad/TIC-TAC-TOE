package xoClientModel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;

public class ENTERBase extends AnchorPane {

    protected final Label playerName;
    protected final Label score;
    protected final Button newGame_txt;
    protected final Button record_txt;

    public ENTERBase() {

        playerName = new Label();
        score = new Label();
        newGame_txt = new Button();
        record_txt = new Button();

        setId("AnchorPane");
        setPrefHeight(400.0);
        setPrefWidth(600.0);
        setStyle("-fx-background-color: #82C3A2;");

        playerName.setLayoutX(61.0);
        playerName.setLayoutY(47.0);
        playerName.setPrefHeight(46.0);
        playerName.setPrefWidth(146.0);
        playerName.setText("PLAYER NAME");
        playerName.setFont(new Font("Cambria Math", 20.0));

        score.setLayoutX(465.0);
        score.setLayoutY(47.0);
        score.setPrefHeight(46.0);
        score.setPrefWidth(102.0);
        score.setText("SCORE");
        score.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        score.setFont(new Font("Cambria Math", 20.0));

        newGame_txt.setLayoutX(212.0);
        newGame_txt.setLayoutY(164.0);
        newGame_txt.setMnemonicParsing(false);
        newGame_txt.setPrefHeight(46.0);
        newGame_txt.setPrefWidth(196.0);
        newGame_txt.setStyle("-fx-background-color: #3FAC76;");
        newGame_txt.setText("NEW GAME");
        newGame_txt.setFont(new Font("Cambria Math", 26.0));

        record_txt.setLayoutX(212.0);
        record_txt.setLayoutY(246.0);
        record_txt.setMnemonicParsing(false);
        record_txt.setPrefHeight(46.0);
        record_txt.setPrefWidth(196.0);
        record_txt.setStyle("-fx-background-color: #3FAC76;");
        record_txt.setText("RECORD");
        record_txt.setFont(new Font("Cambria Math", 25.0));

        getChildren().add(playerName);
        getChildren().add(score);
        getChildren().add(newGame_txt);
        getChildren().add(record_txt);

    }
}
