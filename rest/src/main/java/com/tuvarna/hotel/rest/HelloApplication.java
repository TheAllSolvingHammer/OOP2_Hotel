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

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show ();

    }

    public static void main(String[] args) {
//        Configuration configuration = new Configuration();
//        configuration.configure("hibernate.cfg.xml");
//        configuration.addAnnotatedClass(ServiceEntity.class);
//        SessionFactory sessionFactory = configuration.buildSessionFactory();
//        Session session =  sessionFactory.openSession();
        HibernateUtil.openSession();
        launch();
    }
}