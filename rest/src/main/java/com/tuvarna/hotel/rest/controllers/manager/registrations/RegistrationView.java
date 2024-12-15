package com.tuvarna.hotel.rest.controllers.manager.registrations;

import com.tuvarna.hotel.persistence.enums.ReservationType;
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
import java.util.Date;

public class RegistrationView {

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private TableView<Registration> table;
    @FXML
    private TableColumn<Registration, Integer> id;
    @FXML
    private TableColumn<Registration, Date> start;
    @FXML
    private TableColumn<Registration, Date> end;
    @FXML
    private TableColumn<Registration, Double> price;
    @FXML
    private TableColumn<Registration, ReservationType> phone;

    public void initialize() {
        //todo
    }

    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/manager/manager-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Manager");
        stage.show();
    }
}
