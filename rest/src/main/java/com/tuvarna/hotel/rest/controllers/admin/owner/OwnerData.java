package com.tuvarna.hotel.rest.controllers.admin.owner;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.hotel.DisplayHotelsInput;
import com.tuvarna.hotel.api.models.display.hotel.DisplayHotelsOutput;
import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import com.tuvarna.hotel.api.models.display.owner.Owner;
import com.tuvarna.hotel.core.processes.DisplayHotelProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Setter;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OwnerData{

    private Stage stage;
    private Parent root;
    private Scene scene;
    @Setter
    private Owner owner;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private VBox hotels;

    private final DisplayHotelProcess displayHotelProcess = SingletonManager.getInstance(DisplayHotelProcess.class);

    private CheckComboBox<Hotel> checkComboBox;


    public OwnerData() {
        checkComboBox=new CheckComboBox<>();
    }

    @FXML
    public void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/owners-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();
    }

    @FXML
    public void applyOwner(ActionEvent event) {

    }

    public void display() {
        firstName.setText(owner.getFirstName());
        lastName.setText(owner.getLastName());
        phone.setText(owner.getPhoneNumber());
        email.setText(owner.getEmail());
//        CheckComboBox<Hotel> checkComboBox = new CheckComboBox<>();
        DisplayHotelsInput input= DisplayHotelsInput.builder().build();
        Either<ErrorProcessor, DisplayHotelsOutput> result= displayHotelProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in displaying hotels",error.getMessage());
                    return null;
                },
                success -> {
                    checkComboBox.getItems().addAll(success.getHotelList());
                    return null;
                }
        );
        hotels.getChildren().add(checkComboBox);
    }

}
