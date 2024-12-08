package com.tuvarna.hotel.rest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;

public class AdminController {
    @FXML
    private Button button;
    @FXML
    private Label label;
    @FXML
    private TextField textField;

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    protected void createOwner(){

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
