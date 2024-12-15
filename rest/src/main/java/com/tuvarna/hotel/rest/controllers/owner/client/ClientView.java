package com.tuvarna.hotel.rest.controllers.owner.client;

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

public class ClientView {
    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private TableView<Client> table;
    @FXML
    private TableColumn<Client, String> name;
    @FXML
    private TableColumn<Client, String> surname;
    @FXML
    private TableColumn<Client, String> phone;
    @FXML
    private TableColumn<Client, Integer> stars;

    public void initialize() {
        //todo
    }

    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/owner/owner-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Owner");
        stage.show();
    }
}
