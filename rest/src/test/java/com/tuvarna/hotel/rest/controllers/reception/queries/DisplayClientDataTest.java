package com.tuvarna.hotel.rest.controllers.reception.queries;

import com.tuvarna.hotel.api.models.entities.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import java.time.LocalDate;
import java.util.UUID;

class DisplayClientDataTest extends ApplicationTest {
    private DisplayClientData controller;
    private Client client;
    @Override
    public void start(Stage stage) throws Exception {
        testInput();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/reception/update-client-info.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setClient(client);
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
        client=Client.builder()
                .id(UUID.randomUUID())
                .email("example@gmail.com")
                .phone("0000000000")
                .firstName("Someone")
                .lastName("Someone again")
                .address("Varna")
                .ucn("1234567890")
                .birthDate(LocalDate.now())
                .expireDate(LocalDate.now())
                .issueDate(LocalDate.now())
                .issuedBy("weewsad")
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