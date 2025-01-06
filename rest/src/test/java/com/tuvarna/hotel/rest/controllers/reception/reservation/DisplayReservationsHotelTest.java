package com.tuvarna.hotel.rest.controllers.reception.reservation;

import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.Service;
import com.tuvarna.hotel.core.instantiator.Instantiation;
import com.tuvarna.hotel.core.instantiator.SessionManager;
import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import io.vavr.collection.Array;
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
import java.util.UUID;

class DisplayReservationsHotelTest extends ApplicationTest {
    private DisplayReservationsHotel controller;
    private Hotel hotel;
    @Override
    public void start(Stage stage) throws Exception {
        testInput();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/reception/display-reservations.fxml"));
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
        SessionManager.getInstance().setLoggedInUser(UserEntity.builder().id(UUID.fromString("6cec067b-df20-4949-847a-f6fbff96f47c")).build());
    }

    @Test
    void testInput(){
        this.hotel=Hotel.builder()
                .id(UUID.fromString("556dfb31-641f-478b-a422-1e8a43c3c01b"))
                .name("Admiral")
                .stars(5)
                .location("Golden Sands")
                .serviceList(Array.of(Service.builder().name("Test").price(BigDecimal.valueOf(343)).id(UUID.randomUUID()).build()).toJavaList())
                .userList(Array.of(UUID.fromString("6cec067b-df20-4949-847a-f6fbff96f47c")).toJavaList())
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