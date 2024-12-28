package com.tuvarna.hotel.rest.controllers.admin.owner;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class OwnerAdd {
    private final CreateUserProcess createUserProcess=SingletonManager.getInstance(CreateUserProcess.class);

    private Stage stage;
    private Parent root;
    private Scene scene;

    @FXML
    private AnchorPane anchorPane;
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



    public void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/admin-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Admin");
        stage.show();
    }

    @FXML
    protected void createOwner(ActionEvent event){

        CreateUserInput input = CreateUserInput.builder()
                .firstName(firstName.getText())
                .lastName(lastName.getText())
                .username(username.getText())
                .password(password.getText())
                .passwordSecond(repeatPassword.getText())
                .phone(phoneNumber.getText())
                .email(email.getText())
                .role(RoleType.OWNER)
                .build();

        Either<ErrorProcessor, CreateUserOutput> result= createUserProcess.process(input);
        result.fold(
                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Error in creating owner",error.getMessage());
                    return null;
                },
                success -> {
                    AlertManager.showAlert(Alert.AlertType.CONFIRMATION,"Successfully created owner",success.getMessage());
                    return null;
                }
        );

    }
}
