package com.tuvarna.hotel.rest.controllers.admin;

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

public class AdminController implements Initializable {
    public ListView<String> notificationList;
    private Stage stage;
    Parent root;
    private Scene scene;


    @FXML
    protected void switchToHotelView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/hotels-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hotel View");
        stage.show();
    }

    @FXML
    protected void switchToManagers(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/manager-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Manager View");
        stage.show();
    }

    @FXML
    protected void switchToOwners(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/owners-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Owner View");
        stage.show();
    }

    @FXML
    protected void switchToServices(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/services-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Services view");
        stage.show();
    }

    @FXML
    protected void logOutAdmin(ActionEvent event) throws IOException {

        SessionManager.clearSession();
        AlertManager.clearNotifications();
        root=FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/login/login-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notificationList.setItems(AlertManager.getAlertLog());
    }
}
