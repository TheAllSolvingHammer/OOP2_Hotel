package com.tuvarna.hotel.rest.controllers.admin.owner;

import com.tuvarna.hotel.core.instantiator.Instantiation;
import com.tuvarna.hotel.persistence.connection.HibernateUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.robot.Robot;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

class OwnerViewTest extends ApplicationTest {
    private OwnerView controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/admin/owners-scene.fxml"));
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