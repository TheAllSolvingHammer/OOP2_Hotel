package com.tuvarna.hotel.rest;

import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("./login/login-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show ();

    }

    public static void main(String[] args) {


        HibernateUtil.openSession();
        //InitializeAdmin.addAdmin();
        launch();
    }
}