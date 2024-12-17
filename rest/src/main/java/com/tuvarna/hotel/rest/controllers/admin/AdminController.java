package com.tuvarna.hotel.rest.controllers.admin;

import com.tuvarna.hotel.api.enums.RoleType;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.create.user.CreateUserInput;
import com.tuvarna.hotel.api.models.create.user.CreateUserOutput;
import com.tuvarna.hotel.core.processes.CreateUserProcess;
import com.tuvarna.hotel.domain.encoder.PasswordEncoder;
import com.tuvarna.hotel.rest.contracts.ControllerMarker;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController implements ControllerMarker {

    private Stage stage;
    private Parent root;
    private Scene scene;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField ownerFirstName;
    @FXML
    private TextField ownerLastName;
    @FXML
    private TextField ownerUsername;
    @FXML
    private TextField ownerPassword;
    @FXML
    private TextField ownerRepeatPassword;
    @FXML
    private TextField ownerEmail;
    @FXML
    private TextField ownerPhoneNumber;


    @FXML
    protected void createOwner(ActionEvent event){
        PasswordEncoder passwordEncoder=PasswordEncoder.defaultEncoder();

        CreateUserProcess createUserProcess = new CreateUserProcess();
        CreateUserInput input = CreateUserInput.builder()
                .firstName(ownerFirstName.getText())
                .lastName(ownerLastName.getText())
                .username(ownerUsername.getText())
                .password(ownerPassword.getText())
                .passwordSecond(ownerRepeatPassword.getText())
                .phone(ownerPhoneNumber.getText())
                .email(ownerEmail.getText())
                .role(RoleType.OWNER)
                .build();

        Either<ErrorProcessor, CreateUserOutput> result= createUserProcess.process(input);
        System.out.println(result);

    }
    private void showErrorAlert(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Operation Failed");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }

    @FXML
    protected void switchToBeginning(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/hello-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Navigation");
        stage.show();
    }

    @FXML
    protected void switchToHotelView(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/hotels-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Hotel View");
        stage.show();
    }

    @FXML
    protected void switchToManagers(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/manager-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Manager View");
        stage.show();
    }

    @FXML
    protected void switchToOwners(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/owners-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Owner View");
        stage.show();
    }

    @FXML
    protected void switchToReceptionists(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/com/tuvarna/hotel/rest/admin/receptionists-scene.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Receptionists View");
        stage.show();
    }
}
