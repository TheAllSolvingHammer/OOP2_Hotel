package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.models.entities.ServicesDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class QueryHotelAndServiceTableTest extends ApplicationTest {
    private QueryHotelAndServiceTable controller;
    private List<ServicesDTO> servicesDTOS;
    @Override
    public void start(Stage stage) throws Exception {
        testInputService();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/owner/query-more-service.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setServicesDTOS(servicesDTOS);
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
    void testInputService(){
        servicesDTOS = new ArrayList<>();
        servicesDTOS.add(ServicesDTO.builder()
                        .totalRevenue(BigDecimal.valueOf(1201))
                        .serviceName("Spa")
                        .usageCount(20L)
                .build());
        servicesDTOS.add(ServicesDTO.builder()
                .totalRevenue(BigDecimal.valueOf(120))
                .serviceName("Parking")
                .usageCount(12L)
                .build());
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