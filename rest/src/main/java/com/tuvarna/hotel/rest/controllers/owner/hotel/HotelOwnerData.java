package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import com.tuvarna.hotel.api.models.display.manager.DisplayManagerInput;
import com.tuvarna.hotel.api.models.display.manager.DisplayManagerOutput;
import com.tuvarna.hotel.api.models.display.manager.Manager;
import com.tuvarna.hotel.api.models.display.owner.Owner;
import com.tuvarna.hotel.api.models.display.service.DisplayServicesInput;
import com.tuvarna.hotel.api.models.display.service.DisplayServicesOutput;
import com.tuvarna.hotel.api.models.display.service.Service;
import com.tuvarna.hotel.api.models.get.manager.GetAllUnassignedManagersInput;
import com.tuvarna.hotel.api.models.get.manager.GetAllUnassignedManagersOutput;
import com.tuvarna.hotel.api.models.update.manager.UpdateManagersInput;
import com.tuvarna.hotel.api.models.update.manager.UpdateManagersOutput;
import com.tuvarna.hotel.api.models.update.service.UpdateServicesInput;
import com.tuvarna.hotel.api.models.update.service.UpdateServicesOutput;
import com.tuvarna.hotel.core.processes.*;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotelOwnerData {
    @FXML
    private Label locationHotel;
    @FXML
    private Label stars;
    @FXML
    private Label name;
    private Stage stage;
    private Parent root;
    private Scene scene;
    @Setter
    private Hotel hotel;
    @FXML
    private VBox serviceVbox;
    @FXML
    private VBox managerVbox;

    private final DisplayServicesProcess displayServicesProcess = SingletonManager.getInstance(DisplayServicesProcess.class);
    private final UpdateServicesProcess updateServicesProcess = SingletonManager.getInstance(UpdateServicesProcess.class);
    private final GetUnassignedManagersProcess getUnassignedManagersProcess = SingletonManager.getInstance(GetUnassignedManagersProcess.class);
    private final DisplayManagersProcess displayManagersProcess=SingletonManager.getInstance(DisplayManagersProcess.class);
    private final UpdateManagersProcess updateManagersProcess = SingletonManager.getInstance(UpdateManagersProcess.class);
    private CheckComboBox<Service> checkComboBox;
    private CheckComboBox<Manager> checkComboBoxManager;

    private List<Manager> managerList;

    public HotelOwnerData() {
        this.checkComboBox = new CheckComboBox<>();
        this.checkComboBoxManager = new CheckComboBox<>();
        managerList = new ArrayList<>();
    }

    public void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/owner/hotel-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hotels");
        stage.show();
    }

    public void applyChanges(ActionEvent event) {
        List<Service> serviceList=checkComboBox.getCheckModel().getCheckedItems();
        applyServices(serviceList);

        UpdateManagersInput input=UpdateManagersInput.builder()
                .hotelID(hotel.getId())
                .managerList(managerList)
                .build();
        Either<ErrorProcessor, UpdateManagersOutput> result = updateManagersProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in updating managers ",error.getMessage());
                    return null;
                },
                success -> {
                    AlertManager.showAlert(Alert.AlertType.CONFIRMATION,"Success",success.getMessage());
                    return null;
                }
        );

    }

    private void applyServices(List<Service> serviceList) {
        UpdateServicesInput input = UpdateServicesInput.builder()
                .hotelID(hotel.getId())
                .serviceList(serviceList)
                .build();
        Either<ErrorProcessor, UpdateServicesOutput> result = updateServicesProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in updating services ",error.getMessage());
                    return null;
                },
                success -> {
                    AlertManager.showAlert(Alert.AlertType.CONFIRMATION,"Success",success.getMessage());
                    return null;
                }
        );
    }


    public void display(){
        name.setText(hotel.getName());
        locationHotel.setText(hotel.getLocation());
        stars.setText(hotel.getStars().toString());
        displayServices();
        displayAllManagers();
        checkUnassignedManagers();
    }

    private void checkUnassignedManagers() {
        GetAllUnassignedManagersInput input2 = GetAllUnassignedManagersInput.builder().build();
        Either<ErrorProcessor, GetAllUnassignedManagersOutput> result2= getUnassignedManagersProcess.process(input2);
        result2.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in checking managers ",error.getMessage());
                    return null;
                },
                success -> {
                    managerList.stream()
                            .filter(m -> success.getManagerlist()
                                    .contains(m))
                            .forEach(m -> checkComboBoxManager.getCheckModel()
                                    .check(m));
                return null;
                }
        );
        managerVbox.getChildren().add(checkComboBoxManager);
    }

    private void displayServices() {
        DisplayServicesInput input= DisplayServicesInput.builder().build();
        Either<ErrorProcessor, DisplayServicesOutput> result= displayServicesProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying services ",error.getMessage());
                    return null;
                },
                success -> {
                    checkComboBox.getItems().addAll(success.getServiceList());
                    if (hotel.getServiceList() != null && !hotel.getServiceList().isEmpty()) {
                        success.getServiceList().stream()
                                .filter(c -> hotel.getServiceList()
                                        .contains(c))
                                .forEach(c -> checkComboBox.getCheckModel()
                                        .check(c));
                    }
                    return null;
                }
        );
        serviceVbox.getChildren().add(checkComboBox);
    }

    private void displayAllManagers(){
        DisplayManagerInput input =DisplayManagerInput.builder().build();
        Either<ErrorProcessor, DisplayManagerOutput> result=displayManagersProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying managers ",error.getMessage());
                    return null;
                },
                success -> {
                    checkComboBoxManager.getItems().addAll(success.getManagerList());
                    managerList.addAll(success.getManagerList());
                    return null;
                }
        );
    }
}
