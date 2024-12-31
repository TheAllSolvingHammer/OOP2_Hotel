package com.tuvarna.hotel.rest.controllers.reception.queries;

import com.tuvarna.hotel.api.enums.RatingClient;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.entities.Client;
import com.tuvarna.hotel.api.models.update.client.UpdateClientInput;
import com.tuvarna.hotel.api.models.update.client.UpdateClientOutput;
import com.tuvarna.hotel.core.processes.UpdateClientProcess;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.rest.alert.AlertManager;
import io.vavr.control.Either;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.Setter;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DisplayClientData implements Initializable {
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label ucn;
    @FXML
    private Label email;
    @FXML
    private Label birthDate;
    @FXML
    private Label issueDate;
    @FXML
    private Label expireDate;
    @FXML
    private Label issuedBy;
    @FXML
    private ComboBox<RatingClient> ratingClient;
    @Setter
    private Client client;

    private final UpdateClientProcess updateClientProcess = SingletonManager.getInstance(UpdateClientProcess.class);

    @FXML
    private void switchToTable(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void setClientInfo() {
        firstName.setText(client.getFirstName());
        lastName.setText(client.getLastName());
        phone.setText(client.getPhone());
        address.setText(client.getAddress());
        ucn.setText(client.getUcn());
        email.setText(client.getEmail());
        issuedBy.setText(client.getIssuedBy());
        birthDate.setText(client.getBirthDate().toString());
        issueDate.setText(client.getIssueDate().toString());
        expireDate.setText(client.getExpireDate().toString());
        ratingClient.getSelectionModel().select(client.getRating());

    }

@Override
public void initialize(URL url, ResourceBundle resourceBundle) {
    ObservableList<RatingClient> data= FXCollections.observableArrayList(RatingClient.getMap().values());
        ratingClient.setItems(data);
    }

    public void updateClient(ActionEvent event) throws IOException {
        UpdateClientInput input= UpdateClientInput.builder()
                .client(client.getId())
                .ratingClient(ratingClient.getValue())
                .build();
        Either<ErrorProcessor, UpdateClientOutput> result= updateClientProcess.process(input);

        result.fold(
                error ->{
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Cannot show client",error.getMessage());
                    return null;
                },
                success ->{
                    AlertManager.showAlert(Alert.AlertType.CONFIRMATION,"Success",success.getMessage());
                    return null;
                }
        );
    }

    public void display() {
        setClientInfo();
    }

}
