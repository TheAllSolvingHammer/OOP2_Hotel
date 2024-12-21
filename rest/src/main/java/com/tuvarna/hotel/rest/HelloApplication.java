package com.tuvarna.hotel.rest;

import com.tuvarna.hotel.domain.aspect.LogExecution;
import com.tuvarna.hotel.domain.aspect.LoggingAspect;
import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import com.tuvarna.hotel.persistence.initializer.InitializeAdmin;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
@Slf4j
public class HelloApplication extends Application {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Override
    public void start(Stage stage) throws IOException {
        dosmth();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("./login/login-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show ();

    }

    public static void main(String[] args) {

        logger.info("az");
        HibernateUtil.openSession();
        //InitializeAdmin.addAdmin();
        launch();
    }
    @LogExecution
    public void dosmth() {

    }
}