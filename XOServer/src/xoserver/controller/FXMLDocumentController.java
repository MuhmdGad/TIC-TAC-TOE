/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Atef
 */
public class FXMLDocumentController implements Initializable {

    private Label label;
    @FXML
    private RadioButton btnOn;
    @FXML
    private RadioButton btnOff;
    @FXML
    private Text txtServerStatus;
    @FXML
    private PieChart usersChart;
    private Text txtServerStatus1;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Wow Java is pretty");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
