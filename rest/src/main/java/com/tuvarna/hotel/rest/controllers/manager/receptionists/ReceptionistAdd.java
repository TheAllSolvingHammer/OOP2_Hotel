package com.tuvarna.hotel.rest.controllers.manager.receptionists;

import com.tuvarna.hotel.api.enums.RoleType;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.create.user.CreateUserInput;
import com.tuvarna.hotel.api.models.create.user.CreateUserOutput;
import com.tuvarna.hotel.core.processes.CreateUserProcess;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ReceptionistAdd {
    private Stage stage;
    private Parent root;
    private Scene scene;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repeatPassword;
    @FXML
    private TextField email;
    @FXML
    private TextField phoneNumber;

    private final CreateUserProcess createUserProcess = SingletonManager.getInstance(CreateUserProcess.class);

    public void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/manager/manager-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Manager");
        stage.show();
    }

    public void addReceptionist(ActionEvent event) {
        CreateUserInput input = CreateUserInput.builder()
                .firstName(firstName.getText())
                .lastName(lastName.getText())
                .username(username.getText())
                .role(RoleType.EMPLOYEE)
                .password(password.getText())
                .passwordSecond(repeatPassword.getText())
                .email(email.getText())
                .phone(phoneNumber.getText())
                .build();

        Either<ErrorProcessor, CreateUserOutput> result=createUserProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in creating receptionist",error.getMessage());
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
        firstName.clear();
        lastName.clear();
        username.clear();
        password.clear();
        repeatPassword.clear();
        email.clear();
        phoneNumber.clear();
    }
}
