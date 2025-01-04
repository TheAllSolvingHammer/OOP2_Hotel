package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.create.hotel.CreateHotelInput;
import com.tuvarna.hotel.api.models.create.hotel.CreateHotelOutput;
import com.tuvarna.hotel.core.instantiator.SessionManager;
import com.tuvarna.hotel.core.processes.CreateHotelProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HotelAdd  implements Initializable {
    public TextField name;
    public TextField locationHotel;
    public ComboBox<Integer> comboRating;
    private Stage stage;
    private Parent root;
    private Scene scene;
    private final CreateHotelProcess createHotelProcess;

    public HotelAdd() {
        createHotelProcess = SingletonManager.getInstance(CreateHotelProcess.class);
    }

    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/owner/hotel-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Owner");
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> options =
                FXCollections.observableArrayList(
                        1,2,3,4,5
                );
        comboRating.setItems(options);
        comboRating.getSelectionModel().select(0);

    }

    @FXML
    public void addHotel(ActionEvent event) {
        CreateHotelInput input = CreateHotelInput.builder()
                .name(name.getText())
                .location(locationHotel.getText())
                .rating(comboRating.getSelectionModel().getSelectedItem())
                .ownerID(SessionManager.getInstance().getLoggedInUser().getId())
                .build();
        Either<ErrorProcessor, CreateHotelOutput> result= createHotelProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in creating hotel",error.getMessage());
                    return null;
                },
                success -> {
                    AlertManager.showAlert(Alert.AlertType.CONFIRMATION,"Success",success.getMessage());
                    clearTextFields();
                    return null;
                }
        );
    }

    public void clearTextFields(){
      name.clear();
      locationHotel.clear();
      comboRating.getSelectionModel().clearSelection();
    }
}
