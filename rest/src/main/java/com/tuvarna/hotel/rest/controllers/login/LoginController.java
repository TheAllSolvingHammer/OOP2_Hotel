package com.tuvarna.hotel.rest.controllers.login;

import com.tuvarna.hotel.domain.encoder.PasswordEncoder;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.rest.contracts.BaseController;
import com.tuvarna.hotel.rest.factory.AbstractLoginControllerFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class LoginController implements BaseController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField field_username;
    @FXML
    private TextField field_password;


    @Override
    public void switchToScene2(ActionEvent event) throws IOException {
        PasswordEncoder passwordEncoder=PasswordEncoder.defaultEncoder();
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        System.out.println(field_username.getText());
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(field_username.getText());
        UserEntity userEntity;
        if(userEntityOptional.isPresent()){
            userEntity=userEntityOptional.get();
        }
        else {
            throw new RuntimeException("Error");
        }
        System.out.println(userEntity);

        if(passwordEncoder.matches(field_password.getText().trim(),userEntity.getHashedPassword())){
            AbstractLoginControllerFactory abstractLoginControllerFactory=new AbstractLoginControllerFactory();
            Parent root= null;
            try {
                root = abstractLoginControllerFactory.getController(userEntity.getRole().name());
                stage=(Stage)((Node)event.getSource()).getScene().getWindow();
                scene=new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }
        else throw new RuntimeException("Incorrect user information");

    }
}
