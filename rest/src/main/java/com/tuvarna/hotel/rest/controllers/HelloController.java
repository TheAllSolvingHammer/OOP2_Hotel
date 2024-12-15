package com.tuvarna.hotel.rest.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    private Stage stage;
    private Parent root;
    private Scene scene;



    @FXML
    protected void switchToAdmin(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("../admin/admin-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();
    }

    @FXML
    protected void switchToManager(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("../manager/manager-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("User");
        stage.show();
    }

    @FXML
    protected void switchToReservation(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("../reception/receptionist-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reservation System");
        stage.show();
    }

    @FXML
    protected void switchToOwner(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("../owner/owner-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Owner Data");
        stage.show();
    }

    @FXML
    protected void switchToLogin(ActionEvent event) throws IOException {
        root= FXMLLoader.load(getClass().getResource("../login/login-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

}