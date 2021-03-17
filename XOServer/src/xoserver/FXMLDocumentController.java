/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xoserver;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.RadioButton;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Atef
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private RadioButton btnOn;
    @FXML
    private RadioButton btnOff;
    @FXML
    private Text txtServerStatus;
    @FXML
    private PieChart usersChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
