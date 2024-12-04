package com.tuvarna.hotel.rest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class ReservationController {

    private Stage stage;

    private Parent root;
    private Scene scene;

    @FXML
    protected void createReservation(){

    }

    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
