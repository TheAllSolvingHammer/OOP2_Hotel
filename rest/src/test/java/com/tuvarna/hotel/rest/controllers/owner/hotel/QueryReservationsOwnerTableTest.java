package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.enums.StatusReservation;
import com.tuvarna.hotel.api.enums.TypeReservation;
import com.tuvarna.hotel.api.models.entities.Reservation;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class QueryReservationsOwnerTableTest extends ApplicationTest {
    private QueryReservationsOwnerTable controller;
    private List<Reservation> reservations;
    @Override
    public void start(Stage stage) throws Exception {
        testInput();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/owner/query-more-reservations.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setReservationsList(reservations);
        controller.display();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeAll
    static void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
    }

    @Test
    void testInput(){
        reservations = new ArrayList<>();
        Reservation r1=Reservation.builder()
                .id(UUID.randomUUID())
                .price(BigDecimal.valueOf(234))
                .room("301")
                .type(TypeReservation.VIP_ONLINE)
                .client("Peter Petrov")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .hotel("Admiral")
                .status(StatusReservation.CANCELED)
                .build();
        Reservation r2=Reservation.builder()
                .id(UUID.randomUUID())
                .price(BigDecimal.valueOf(500))
                .room("302")
                .type(TypeReservation.VIP_REAL)
                .client("Peter Ivanov")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .hotel("Admiral")
                .status(StatusReservation.CONFIRMED)
                .build();
        reservations.add(r1);
        reservations.add(r2);
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