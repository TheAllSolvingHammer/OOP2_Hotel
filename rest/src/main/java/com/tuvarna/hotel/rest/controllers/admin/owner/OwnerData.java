package com.tuvarna.hotel.rest.controllers.admin.owner;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.hotel.DisplayHotelsInput;
import com.tuvarna.hotel.api.models.display.hotel.DisplayHotelsOutput;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.Owner;
import com.tuvarna.hotel.core.processes.DisplayOwnerHotelProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import lombok.Setter;

import java.io.IOException;

public class OwnerData{
    private Stage stage;
    private Parent root;
    private Scene scene;
    @Setter
    private Owner owner;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private ListView<Hotel> hotels;

    private final DisplayOwnerHotelProcess displayHotelProcess;

    public OwnerData() {
        displayHotelProcess = SingletonManager.getInstance(DisplayOwnerHotelProcess.class);
    }

    @FXML
    public void switchToBeginning(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void display() {
        firstName.setText(owner.getFirstName());
        lastName.setText(owner.getLastName());
        phone.setText(owner.getPhoneNumber());
        email.setText(owner.getEmail());
        DisplayHotelsInput input= DisplayHotelsInput.builder().id(owner.getId()).build();
        Either<ErrorProcessor, DisplayHotelsOutput> result= displayHotelProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying hotels",error.getMessage());
                    return null;
                },
                success -> {
                    ObservableList<Hotel> data = FXCollections.observableArrayList(success.getHotelList());
                    hotels.setItems(data);
                    hotels.refresh();
                    return null;
                }
        );
    }
}
