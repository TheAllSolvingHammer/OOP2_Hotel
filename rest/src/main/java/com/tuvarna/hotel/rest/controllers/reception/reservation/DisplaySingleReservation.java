package com.tuvarna.hotel.rest.controllers.reception.reservation;

import com.tuvarna.hotel.api.enums.StatusReservation;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.entities.Reservation;
import com.tuvarna.hotel.api.models.entities.Service;
import com.tuvarna.hotel.api.models.update.reservation.UpdateReservationInput;
import com.tuvarna.hotel.api.models.update.reservation.UpdateReservationOutput;
import com.tuvarna.hotel.core.processes.UpdateReservationProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lombok.Setter;

public class DisplaySingleReservation {
    public Label id;
    public Label startDate;
    public Label type;
    public Label endDate;
    public Label price;
    public Label room;
    public Label hotel;
    public Label client;
    public ListView<Service> services;
    public ComboBox<StatusReservation> status;
    @Setter
    private Reservation reservation;
    private final UpdateReservationProcess reservationProcess;

    public DisplaySingleReservation() {
        reservationProcess= SingletonManager.getInstance(UpdateReservationProcess.class);
    }

    public void handleBackButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void display(){
        id.setText(reservation.getId().toString());
        startDate.setText(reservation.getStartDate().toString());
        endDate.setText(reservation.getEndDate().toString());
        type.setText(reservation.getType().name());
        price.setText(reservation.getPrice().toString());
        room.setText(reservation.getRoom());
        hotel.setText(reservation.getHotel());
        client.setText(reservation.getClient());
        ObservableList<Service> data =FXCollections.observableArrayList(reservation.getServices());
        services.setItems(data);
        services.refresh();
        ObservableList<StatusReservation> statusReservations = FXCollections.observableArrayList(StatusReservation.getMap().values());
        status.setItems(statusReservations);
        status.getSelectionModel().select(reservation.getStatus());
    }

    public void applyChanges(ActionEvent event) {
        UpdateReservationInput input = UpdateReservationInput.builder()
                .reservationID(reservation.getId())
                .status(status.getValue())
                .build();
        Either<ErrorProcessor, UpdateReservationOutput> result = reservationProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in updating reservation",error.getMessage());
                    return null;
                },
                success -> {
                    AlertManager.showAlert(Alert.AlertType.CONFIRMATION,"Success",success.getMessage());
                    return null;
                });
    }
}
