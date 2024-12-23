package com.tuvarna.hotel.rest.controllers.reception;

import com.tuvarna.hotel.rest.contracts.ControllerMarker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ReceptionistController implements ControllerMarker {

    private Stage stage;

    private Parent root;
    private Scene scene;

    @FXML
    protected void createReservation(){

    }

    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/hello-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Navigation");
        stage.show();
    }

    @FXML
    protected void switchToReservations(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/reception/reservations-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reservations View");
        stage.show();
    }
}
