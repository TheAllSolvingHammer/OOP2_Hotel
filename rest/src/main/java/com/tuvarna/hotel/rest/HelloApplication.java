package com.tuvarna.hotel.rest;

import com.tuvarna.hotel.core.instantiator.Instantiation;
import com.tuvarna.hotel.domain.aspect.LoggingAspect;
import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import com.tuvarna.hotel.persistence.initializer.InitializeServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HelloApplication extends Application {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("./login/login-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show ();

    }

    public static void main(String[] args) {

        HibernateUtil.openSession();
        Instantiation.loadInstances();
        InitializeServices initializer = new InitializeServices();
        initializer.initializeData();
        //InitializeAdmin.addAdmin();
        launch();
    }

}