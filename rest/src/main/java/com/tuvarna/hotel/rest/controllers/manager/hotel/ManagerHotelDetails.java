package com.tuvarna.hotel.rest.controllers.manager.hotel;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.receptionist.DisplayAllEmployeeInput;
import com.tuvarna.hotel.api.models.display.receptionist.DisplayAllEmployeeOutput;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.Receptionist;
import com.tuvarna.hotel.api.models.get.receptionist.assigned.GetAssignedEmployeeInput;
import com.tuvarna.hotel.api.models.get.receptionist.assigned.GetAssignedEmployeeOutput;
import com.tuvarna.hotel.api.models.update.receptionist.UpdateReceptionistOfHotelInput;
import com.tuvarna.hotel.api.models.update.receptionist.UpdateReceptionistOfHotelOutput;
import com.tuvarna.hotel.core.processes.DisplayAllEmployeeProcessor;
import com.tuvarna.hotel.core.processes.GetAssignedEmployeesProcess;
import com.tuvarna.hotel.core.processes.UpdateReceptionistOfHotelProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;
import org.controlsfx.control.CheckComboBox;

import java.util.ArrayList;
import java.util.List;

public class ManagerHotelDetails {
    @FXML
    private VBox receptionistVbox;
    @FXML
    private Label locationHotel;
    @FXML
    private Label stars;
    @FXML
    private Label name;
    @Setter
    private Hotel hotel;
    private CheckComboBox<Receptionist> checkComboBox;
    private List<Receptionist> receptionistList;
    private final GetAssignedEmployeesProcess getEmployeesProcess;
    private final DisplayAllEmployeeProcessor displayAllEmployeeProcessor;
    private final UpdateReceptionistOfHotelProcess updateHotelProcess;
    public ManagerHotelDetails() {
        checkComboBox=new CheckComboBox<>();
        receptionistList=new ArrayList<>();
        getEmployeesProcess = SingletonManager.getInstance(GetAssignedEmployeesProcess.class);
        displayAllEmployeeProcessor = SingletonManager.getInstance(DisplayAllEmployeeProcessor.class);
        updateHotelProcess = SingletonManager.getInstance(UpdateReceptionistOfHotelProcess.class);
    }

    public void applyChanges(ActionEvent event) {
        List<Receptionist> receptionists=checkComboBox.getCheckModel().getCheckedItems();
        UpdateReceptionistOfHotelInput input = UpdateReceptionistOfHotelInput.builder()
                .hotelID(hotel.getId())
                .receptionistList(receptionists)
                .build();
        Either<ErrorProcessor, UpdateReceptionistOfHotelOutput> result=updateHotelProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in updating hotel info ",error.getMessage());
                    return null;
                },
                success -> {
                    AlertManager.showAlert(Alert.AlertType.CONFIRMATION,"Success",success.getMessage());
                    return null;
                }
        );
    }

    public void handleBackButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public void display(){
        name.setText(hotel.getName());
        locationHotel.setText(hotel.getLocation());
        stars.setText(hotel.getStars().toString());
        displayAllReceptionists();
        checkAllReceptionist();
    }

    private void checkAllReceptionist() {
        GetAssignedEmployeeInput input = GetAssignedEmployeeInput.builder()
                .hotel(hotel)
                .build();
        Either<ErrorProcessor, GetAssignedEmployeeOutput> result = getEmployeesProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in checking receptionists ",error.getMessage());
                    return null;
                },
                success -> {
                    for (Receptionist m : receptionistList) {
                        if (success.getReceptionistList()
                                .contains(m)) {
                            checkComboBox.getCheckModel()
                                    .check(m);
                        }
                    }
                    return null;
                }
        );
        receptionistVbox.getChildren().add(checkComboBox);
    }

    private void displayAllReceptionists() {
        DisplayAllEmployeeInput input = DisplayAllEmployeeInput.builder()
                .build();
        Either<ErrorProcessor, DisplayAllEmployeeOutput> result = displayAllEmployeeProcessor.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying employee ",error.getMessage());
                    return null;
                },
                success -> {
                    checkComboBox.getItems().addAll(success.getReceptionistList());
                    //
                    System.out.println(success.getReceptionistList());
                    receptionistList.addAll(success.getReceptionistList());
                    return null;
                }
        );
    }
}
