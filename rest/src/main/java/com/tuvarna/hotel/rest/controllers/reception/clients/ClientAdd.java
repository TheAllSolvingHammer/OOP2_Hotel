package com.tuvarna.hotel.rest.controllers.reception.clients;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.create.client.CreateClientInput;
import com.tuvarna.hotel.api.models.create.client.CreateClientOutput;
import com.tuvarna.hotel.core.processes.CreateClientProcess;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ClientAdd {
    @FXML
    public TextField firstName;
    @FXML
    public TextField lastName;
    @FXML
    public TextField phone;
    @FXML
    public TextField ucn;
    @FXML
    public TextField address;
    @FXML
    public TextField email;
    @FXML
    public DatePicker birthDate;
    @FXML
    public DatePicker issueDate;
    @FXML
    public DatePicker expireDate;
    @FXML
    public TextField issuedBy;

    private Stage stage;
    private Parent root;
    private Scene scene;

    private final CreateClientProcess createClientProcess = SingletonManager.getInstance(CreateClientProcess.class);

    @FXML
    public void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/reception/receptionist-view.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Manager");
        stage.show();
    }

    public void addClient(ActionEvent event) {



            CreateClientInput input = CreateClientInput.builder()
                    .firstName(firstName.getText())
                    .lastName(lastName.getText())
                    .phone(phone.getText())
                    .ucn(ucn.getText())
                    .address(address.getText())
                    .email(email.getText())
                    .birthDate(birthDate.getValue())
                    .issueDate(issueDate.getValue())
                    .expireDate(expireDate.getValue())
                    .issuedBy(issuedBy.getText())
                    .build();

            Either<ErrorProcessor, CreateClientOutput> result = createClientProcess.process(input);

            result.fold(
                    error -> {
                        AlertManager.showAlert(Alert.AlertType.ERROR, "Error in creating client", error.getMessage());
                        return null;
                    },
                    success -> {
                        AlertManager.showAlert(Alert.AlertType.CONFIRMATION, "Client created", success.getMessage());
                        return null;
                    }
            );
    }
}