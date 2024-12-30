package com.tuvarna.hotel.rest.controllers.reception;

import com.tuvarna.hotel.core.instantiator.SessionManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionistController implements Initializable {

    public ListView<String> notificationList;
    private Stage stage;

    private Parent root;
    private Scene scene;

    @FXML
    protected void createReservation(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/reception/hotel-receptionist.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reservation View");
        stage.show();

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
    protected void switchToClients(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/reception/add-client.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Client View");
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

    @FXML
    public void logOutReceptionist(ActionEvent event) throws IOException {

        SessionManager.clearSession();
        AlertManager.clearNotifications();
        root=FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/login/login-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notificationList.setItems(AlertManager.getAlertLog());
    }
}
