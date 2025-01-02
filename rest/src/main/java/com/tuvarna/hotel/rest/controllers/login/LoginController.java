package com.tuvarna.hotel.rest.controllers.login;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.login.LoginUserInput;
import com.tuvarna.hotel.api.models.login.LoginUserOutput;
import com.tuvarna.hotel.core.processes.LoginUserProcess;
import com.tuvarna.hotel.rest.alert.AlertManager;
import com.tuvarna.hotel.rest.factory.AbstractLoginControllerFactory;
import io.vavr.control.Either;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController  {
    private final LoginUserProcess loginUserProcess = new LoginUserProcess();

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField field_username;
    @FXML
    private PasswordField field_password;


    @FXML
    public void switchToScene2(ActionEvent event)  {
        LoginUserInput input = LoginUserInput.builder()
                .username(field_username.getText())
                .password(field_password.getText())
                .build();
        Either<ErrorProcessor, LoginUserOutput> result=loginUserProcess.process(input);
        result.fold(

                error -> {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Login error",error.getMessage());
                    return null;
                },

                success -> {
                    AbstractLoginControllerFactory abstractLoginControllerFactory=new AbstractLoginControllerFactory();
                root= null;
            try {
                root = abstractLoginControllerFactory.getController(success.getRole().name());
                stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                scene=new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
                    return null;
                }
        );


    }
}
