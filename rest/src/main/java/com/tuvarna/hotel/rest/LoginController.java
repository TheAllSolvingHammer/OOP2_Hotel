package com.tuvarna.hotel.rest;

import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class LoginController implements BaseController{
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField field_username;
    @FXML
    private TextField field_password;

    @Override
    public void switchToScene1(ActionEvent event) throws IOException {
//       Parent root= FXMLLoader.load(getClass().getResource("login-scene.fxml"));
//       stage=(Stage)((Node)event.getSource()).getScene().getWindow();
//       scene=new Scene(root);
//       stage.setScene(scene);
//       stage.show();
    }

    @Override
    public void switchToScene2(ActionEvent event) throws IOException{
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
        if(field_password.getText().equals(userEntity.getHashedPassword())){
            Parent root= FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            stage=(Stage)((Node)event.getSource()).getScene().getWindow();
            scene=new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else throw new RuntimeException("Incorrect user information");

    }
}
