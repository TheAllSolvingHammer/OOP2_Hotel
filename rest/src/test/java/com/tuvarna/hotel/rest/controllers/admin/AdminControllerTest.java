package com.tuvarna.hotel.rest.controllers.admin;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

public class AdminControllerTest extends ApplicationTest {

    private AdminController adminController;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/admin/admin-view.fxml"));
        Parent root = loader.load();
        adminController = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void setUp() throws Exception {
        FxToolkit.registerPrimaryStage();
    }

    @Test
    void testMouseClickSwitchToHotelView() throws Exception {
            try {
                Robot robot = new Robot();
                robot.mouseMove(900, 500);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        Thread.sleep(100);
    }
}