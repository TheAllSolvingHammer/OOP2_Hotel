package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.Service;
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

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

class HotelOwnerDataTest extends ApplicationTest {
    private HotelOwnerData controller;
    private Hotel hotel;
    @Override
    public void start(Stage stage) throws Exception {
        testHotel();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/owner/more-hotel-owner-info.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setHotel(hotel);
        controller.display();
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
    void testHotel(){
        hotel=Hotel.builder()
                .id(UUID.randomUUID())
                .stars(4)
                .name("Fake hotel")
                .location("Golden sands")
                .userList(List.of(UUID.fromString("f289fcf3-d52d-499f-857f-f705bdae28e7")))
                .serviceList(List.of(Service.builder()
                                .price(BigDecimal.valueOf(23))
                                .id(UUID.randomUUID())
                                .name("Spa")
                        .build()))
                .build();
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