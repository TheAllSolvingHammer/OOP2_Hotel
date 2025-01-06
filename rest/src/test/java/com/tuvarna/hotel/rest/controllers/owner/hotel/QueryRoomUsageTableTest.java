package com.tuvarna.hotel.rest.controllers.owner.hotel;

import com.tuvarna.hotel.api.enums.TypeRoom;
import com.tuvarna.hotel.api.models.entities.RoomQueryDTO;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class QueryRoomUsageTableTest extends ApplicationTest {
    private QueryRoomUsageTable controller;
    private List<RoomQueryDTO> roomQueryDTOS;
    public QueryRoomUsageTableTest() {
        roomQueryDTOS= new ArrayList<>();
    }

    @Override
    public void start(Stage stage) throws Exception {
        testArrayList();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tuvarna/hotel/rest/owner/query-more-room-usage.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        controller.setRoomQueryDTOS(roomQueryDTOS);
        controller.display();
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
    void testArrayList(){
        roomQueryDTOS=new ArrayList<>();
        roomQueryDTOS.add(RoomQueryDTO.builder()
                .usageCount(1L)
                .price(BigDecimal.valueOf(234.5))
                .roomType(TypeRoom.SINGLE)
                .roomNumber("123-TEST ROOM")
                .build());

        roomQueryDTOS.add(RoomQueryDTO.builder()
                .usageCount(10L)
                .price(BigDecimal.valueOf(1235))
                .roomType(TypeRoom.DOUBLE)
                .roomNumber("234-TEST ROOM")
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