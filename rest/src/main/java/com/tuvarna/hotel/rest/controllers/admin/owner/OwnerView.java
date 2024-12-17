package com.tuvarna.hotel.rest.controllers.admin.owner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class OwnerView {
    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private TableView<Owner> table;
    @FXML
    private TableColumn<Owner,Integer> id;
    @FXML
    private TableColumn<Owner, String> name;
    @FXML
    private TableColumn<Owner, String> surname;
    @FXML
    private TableColumn<Owner, Integer> email;
    @FXML
    private TableColumn<Owner, String> phone;

    public void initialize() {
        //todo
    }

    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/admin-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();
    }
}