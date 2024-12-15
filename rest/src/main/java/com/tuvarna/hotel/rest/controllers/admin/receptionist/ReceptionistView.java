package com.tuvarna.hotel.rest.controllers.admin.receptionist;

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

public class ReceptionistView {
    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private TableView<Receptionist> table;
    @FXML
    private TableColumn<Receptionist,Integer> id;
    @FXML
    private TableColumn<Receptionist, String> name;
    @FXML
    private TableColumn<Receptionist, String> surname;
    @FXML
    private TableColumn<Receptionist, Integer> email;
    @FXML
    private TableColumn<Receptionist, String> phone;

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
