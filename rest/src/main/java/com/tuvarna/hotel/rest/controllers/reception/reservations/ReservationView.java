package com.tuvarna.hotel.rest.controllers.reception.reservations;

import com.tuvarna.hotel.persistence.enums.ReservationType;
import com.tuvarna.hotel.rest.controllers.reception.reservation.Reservation;
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

public class ReservationView {

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private TableView<Reservation> table;
    @FXML
    private TableColumn<Reservation, Integer> id;
    @FXML
    private TableColumn<Reservation, Date> start;
    @FXML
    private TableColumn<Reservation, Date> end;
    @FXML
    private TableColumn<Reservation, Double> price;
    @FXML
    private TableColumn<Reservation, ReservationType> phone;

    public void initialize() {
        //todo
    }

    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/reception/receptionist-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Reception");
        stage.show();
    }
}