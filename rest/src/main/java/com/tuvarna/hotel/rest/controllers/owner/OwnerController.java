package com.tuvarna.hotel.rest.controllers.owner;


import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.entities.Reservation;
import com.tuvarna.hotel.api.models.get.reservation.ExpiringReservationInput;
import com.tuvarna.hotel.api.models.get.reservation.ExpiringReservationOutput;
import com.tuvarna.hotel.core.instantiator.SessionManager;
import com.tuvarna.hotel.core.processes.ExpiringReservationProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class OwnerController implements Initializable {
    @FXML
    private ListView<String> notificationList;
    private Stage stage;
    private Parent root;
    private Scene scene;
    private final ExpiringReservationProcess expiringReservationProcess;

    public OwnerController() {
        expiringReservationProcess = SingletonManager.getInstance(ExpiringReservationProcess.class);
    }

    @FXML
    protected void switchToHotels(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/owner/hotel-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hotel View");
        stage.show();
    }


    public void createManager(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/owner/add-manager.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hotel Information");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notificationList.setItems(AlertManager.getAlertLog());
        if(!SessionManager.flag) return;
        ExpiringReservationInput input = ExpiringReservationInput.builder()
                .date(LocalDate.now())
                .userID(SessionManager.getInstance().getLoggedInUser().getId())
                .build();
        Either<ErrorProcessor, ExpiringReservationOutput> result=expiringReservationProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in obtaining expiring reservations",error.getMessage());
                    return null;
                },
                success -> {
                    List<Reservation> reservationList = success.getReservations();
                    for (Reservation r : reservationList) {
                        notificationList.getItems().add("Expiring reservation: " + r.toString());
                    }
                    if(!reservationList.isEmpty()) {
                        AlertManager.showAlert(Alert.AlertType.WARNING, "Expiring reservations", "Today's expiring reservations are: " + reservationList);
                    }
                    SessionManager.flag=false;
                    return null;
                }
        );
    }

    @FXML
    public void logOutOwner(ActionEvent event) throws IOException {
        SessionManager.clearSession();
        AlertManager.clearNotifications();
        root=FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/login/login-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    public void switchToDetails(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/owner/hotel-details.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hotel Details");
        stage.show();
    }

    public void queryReceptionists(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/owner/query-hotel-receptionist.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Query Receptionist");
        stage.show();
    }

    public void queryReservations(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/owner/query-hotel-service.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hotel Reservation");
        stage.show();
    }

    public void queryRoomsUsage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/owner/query-room-usage.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Room usage ");
        stage.show();
    }
}
