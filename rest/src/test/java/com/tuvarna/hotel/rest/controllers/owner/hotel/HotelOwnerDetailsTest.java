package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.core.instantiator.Instantiation;
import com.tuvarna.hotel.core.instantiator.SessionManager;
import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.UUID;

class HotelOwnerDetailsTest extends ApplicationTest {
    private HotelOwnerDetails controller;
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/owner/hotel-details.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeAll
    static void setUp() throws Exception {
        HibernateUtil.openSession();
        Instantiation.loadInstances();
        FxToolkit.registerPrimaryStage();
        SessionManager.getInstance().setLoggedInUser(UserEntity.builder().id(UUID.fromString("f289fcf3-d52d-499f-857f-f705bdae28e7")).build());
    }

    @Test
    void testMouseClickSwitchToHotelView() throws Exception {
        try {
            Robot robot = new Robot();
            robot.mouseMove(900, 500);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Thread.sleep(1000);
    }
}