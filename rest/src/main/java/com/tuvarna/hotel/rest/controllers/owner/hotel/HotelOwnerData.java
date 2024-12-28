package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import com.tuvarna.hotel.api.models.display.manager.DisplayManagerInput;
import com.tuvarna.hotel.api.models.display.manager.DisplayManagerOutput;
import com.tuvarna.hotel.api.models.display.manager.Manager;
import com.tuvarna.hotel.api.models.display.service.DisplayServicesInput;
import com.tuvarna.hotel.api.models.display.service.DisplayServicesOutput;
import com.tuvarna.hotel.api.models.display.service.Service;
import com.tuvarna.hotel.api.models.get.manager.GetAllHotelManagersInput;
import com.tuvarna.hotel.api.models.get.manager.GetAllHotelManagersOutput;
import com.tuvarna.hotel.api.models.update.service.UpdateHotelInput;
import com.tuvarna.hotel.api.models.update.service.UpdateHotelOutput;
import com.tuvarna.hotel.core.processes.*;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private final UpdateHotelProcess updateServicesProcess = SingletonManager.getInstance(UpdateHotelProcess.class);
    private final GetHotelManagersProcess getHotelManagersProcess = SingletonManager.getInstance(GetHotelManagersProcess.class);
    private final DisplayManagersProcess displayManagersProcess=SingletonManager.getInstance(DisplayManagersProcess.class);
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

    @FXML
    private void handleBackButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void applyChanges(ActionEvent event) {
        List<Service> serviceList=checkComboBox.getCheckModel().getCheckedItems();
        List<Manager> managerList2=checkComboBoxManager.getCheckModel().getCheckedItems();
        applyAll(serviceList,managerList2);


    }

    private void applyAll(List<Service> serviceList,List<Manager> managers) {
        UpdateHotelInput input = UpdateHotelInput.builder()
                .hotelID(hotel.getId())
                .serviceList(serviceList)
                .managerList(managers)
                .build();
        Either<ErrorProcessor, UpdateHotelOutput> result = updateServicesProcess.process(input);
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


    public void display(){
        name.setText(hotel.getName());
        locationHotel.setText(hotel.getLocation());
        stars.setText(hotel.getStars().toString());
        displayServices();
        displayAllManagers();
        checkAssignedManagers();
    }

    private void checkAssignedManagers() {
        GetAllHotelManagersInput input2 = GetAllHotelManagersInput.builder().hotel(hotel).build();

        Either<ErrorProcessor, GetAllHotelManagersOutput> result2= getHotelManagersProcess.process(input2);
        result2.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in checking managers ",error.getMessage());
                    return null;
                },
                success -> {
                    System.out.println(success.getManagerlist());
                    for (Manager m : managerList) {
                        if (success.getManagerlist()
                                .contains(m)) {
                            checkComboBoxManager.getCheckModel()
                                    .check(m);
                        }
                    }
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
