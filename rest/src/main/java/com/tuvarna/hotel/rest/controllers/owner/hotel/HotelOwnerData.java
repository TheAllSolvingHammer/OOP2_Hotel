package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import com.tuvarna.hotel.api.models.display.owner.Owner;
import com.tuvarna.hotel.api.models.display.service.DisplayServicesInput;
import com.tuvarna.hotel.api.models.display.service.DisplayServicesOutput;
import com.tuvarna.hotel.api.models.display.service.Service;
import com.tuvarna.hotel.api.models.update.service.UpdateServicesInput;
import com.tuvarna.hotel.api.models.update.service.UpdateServicesOutput;
import com.tuvarna.hotel.core.processes.DisplayServicesProcess;
import com.tuvarna.hotel.core.processes.UpdateServicesProcess;
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
import java.util.List;

public class HotelOwnerData {
    @FXML
    private ComboBox<Owner> ownerCheckBox;
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

    private final DisplayServicesProcess displayServicesProcess = SingletonManager.getInstance(DisplayServicesProcess.class);
    private final UpdateServicesProcess updateServicesProcess = SingletonManager.getInstance(UpdateServicesProcess.class);
    private final UserRepositoryImpl userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
    private CheckComboBox<Service> checkComboBox;

    public HotelOwnerData() {
        this.checkComboBox = new CheckComboBox<>();
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
        System.out.println(serviceList);
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
        DisplayServicesInput input= DisplayServicesInput.builder().build();
        Either<ErrorProcessor, DisplayServicesOutput> result= displayServicesProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying services ",error.getMessage());
                    return null;
                },
                success -> {
                    checkComboBox.getItems().addAll(success.getServiceList());
                    System.out.println(hotel.getServiceList());
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
        //todo display the owners also
    }


}
