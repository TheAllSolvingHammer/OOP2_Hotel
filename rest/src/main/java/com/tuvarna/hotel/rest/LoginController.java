package com.tuvarna.hotel.rest;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController implements BaseController{

    private Stage stage;
    private Scene scene;
    private Parent root;


    @Override
    public void switchToScene1(ActionEvent event) throws IOException {
       Parent root= FXMLLoader.load(getClass().getResource("login-scene.fxml"));
       stage=(Stage)((Node)event.getSource()).getScene().getWindow();
       scene=new Scene(root);
       stage.setScene(scene);
       stage.show();
    }

    @Override
    public void switchToScene2(ActionEvent event) throws IOException{
        Parent root= FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
