package xoserver.view;

import javafx.scene.chart.PieChart;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.chart.PieChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Toggle;
import xoserver.model.DatabaseConnection;
import xoserver.model.GameHandler;
import xoserver.model.MainServer;
import javafx.beans.binding.Bindings;

public class ServerGUI extends AnchorPane {

    protected final RadioButton btnOn;
    protected final RadioButton btnOff;
    protected final Text text;
    protected final Text txtServerStatus;
    protected final PieChart usersChart;
    protected final Text text0;
    protected final ToggleGroup group;
    private DatabaseConnection databaseConnection;
    ObservableList<PieChart.Data> pieChartData;
    public static ScheduledExecutorService scheduledExecutorService;
    static boolean isServerOn;

    public ServerGUI() {
        isServerOn = false;
        btnOn = new RadioButton();
        btnOff = new RadioButton();
        text = new Text();
        txtServerStatus = new Text();
        text0 = new Text();
        group = new ToggleGroup();

        databaseConnection = DatabaseConnection.getDatabaseInstance();
        databaseConnection.openConnection();  //initialize database with server
        pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Offline", databaseConnection.numOfflinePlayers()),
                new PieChart.Data("Online", databaseConnection.numOnlinePlayers()) //initialize pie chart 
        );
        usersChart = new PieChart(pieChartData);
        usersChart.setVisible(false);
        usersChart.setAnimated(false);

        setId("AnchorPane");
        setPrefHeight(498.0);
        setPrefWidth(680.0);
        getStyleClass().add("bodybg3");
        getStylesheets().add("/Style/BackgroundServer.css");

        btnOn.setLayoutX(511.0);
        btnOn.setLayoutY(266.0);
        btnOn.setMnemonicParsing(false);
        btnOn.setText("ON");
        btnOn.setTextFill(javafx.scene.paint.Color.valueOf("#0a7c26"));

        btnOff.setLayoutX(511.0);
        btnOff.setLayoutY(295.0);
        btnOff.setMnemonicParsing(false);
        btnOff.setText("OFF");
        btnOff.setTextFill(javafx.scene.paint.Color.RED);

        text.setFill(javafx.scene.paint.Color.WHITE);
        text.setLayoutX(135.0);
        text.setLayoutY(105.0);
        text.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text.setStrokeWidth(0.0);
        text.setText("TIC TAC TOE SERVER");
        text.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        text.setWrappingWidth(410.13671875);
        text.setFont(new Font("Cambria Math", 40.0));

        AnchorPane.setRightAnchor(txtServerStatus, 37.86328125);
        txtServerStatus.setFill(javafx.scene.paint.Color.WHITE);
        txtServerStatus.setLayoutX(422.0);
        txtServerStatus.setLayoutY(244.0);
        txtServerStatus.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        txtServerStatus.setStrokeWidth(0.0);
        txtServerStatus.setText("Server is OFF");
        txtServerStatus.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        txtServerStatus.setWrappingWidth(220.13671875);
        txtServerStatus.setFont(new Font("Cambria Math", 20.0));

        usersChart.setLayoutX(74.0);
        usersChart.setLayoutY(178.0);
        usersChart.setLegendSide(javafx.geometry.Side.LEFT);
        usersChart.setPrefHeight(234.0);
        usersChart.setPrefWidth(304.0);
        usersChart.setTitleSide(javafx.geometry.Side.BOTTOM);

        text0.setFill(javafx.scene.paint.Color.WHITE);
        text0.setLayoutX(110.0);
        text0.setLayoutY(428.0);
        text0.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        text0.setStrokeWidth(0.0);
        text0.setText("Users Chart");
        text0.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        text0.setWrappingWidth(220.13671875);
        text0.setFont(new Font("Cambria Math", 20.0));

        getChildren().add(btnOn);
        getChildren().add(btnOff);
        getChildren().add(text);
        getChildren().add(txtServerStatus);
        getChildren().add(usersChart);
        getChildren().add(text0);

        //added parts
        btnOn.setToggleGroup(group);
        btnOff.setToggleGroup(group);
        btnOff.setSelected(true);

        togglingButtons();

        btnOn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MainServer.getInstance().start();
                scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
                refreshPieChart();
                usersChart.setVisible(true);
                databaseConnection.setOpeningServer();
                isServerOn = true;
            }
        });

        btnOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                usersChart.setVisible(false);
                try {
                    closingEverything();
                } catch (IOException ex) {
                    Logger.getLogger(ServerGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    public void togglingButtons() {
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton tempBtn = (RadioButton) group.getSelectedToggle();
                if (tempBtn != null) {
                    String s = tempBtn.getText();
                    if (s.equals("ON")) {
                        txtServerStatus.setText("Server is running");
                    } else {
                        txtServerStatus.setText("Server is down");
                    }
                }
            }
        });

    }

    public void refreshPieChart() {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // Update the chart
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        // put random number with current time
                        pieChartData.set(0, new PieChart.Data("Offline", databaseConnection.numOfflinePlayers()));
                        pieChartData.set(1, new PieChart.Data("Online", databaseConnection.numOnlinePlayers()));

                        pieChartData.forEach(data
                                -> data.nameProperty().bind(
                                        Bindings.concat(
                                                data.getName(), " ", data.pieValueProperty(), " Players"
                                        )
                                )
                        );
                    }
                });
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    public static void closingEverything() throws IOException {
        if (isServerOn) {
            scheduledExecutorService.shutdown();
        }
        MainServer.getInstance().stop();
        MainServer.mainSocket.close();          //stops main server when server is down (so when client enters server he cant send)
        MainServer.getInstance().stopClients(); //stops sockets threads at clients side
        MainServer.deleteInstance();
        isServerOn = false;
    }
}
