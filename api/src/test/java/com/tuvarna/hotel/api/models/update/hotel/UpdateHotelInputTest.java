package com.tuvarna.hotel.api.models.update.hotel;

import static org.junit.jupiter.api.Assertions.*;

import com.tuvarna.hotel.api.models.entities.Manager;
import com.tuvarna.hotel.api.models.entities.Service;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UpdateHotelInputTest {

    @Test
    public void testConstructorAndGettersSetters() {
        UUID hotelID = UUID.randomUUID();

        UUID managerId = UUID.randomUUID();
        Manager manager = new Manager(managerId, "John", "Doe", "chris.georgiev@example.com", "1234567890", null);

        UUID serviceId = UUID.randomUUID();
        Service service = new Service(serviceId, "Room Cleaning", new BigDecimal("25.00"));

        List<Manager> managerList = List.of(manager);
        List<Service> serviceList = List.of(service);

        UpdateHotelInput input = new UpdateHotelInput(managerList, serviceList, hotelID);

        assertEquals(managerList, input.getManagerList());
        assertEquals(serviceList, input.getServiceList());
        assertEquals(hotelID, input.getHotelID());

        UUID newHotelID = UUID.randomUUID();
        input.setHotelID(newHotelID);
        assertEquals(newHotelID, input.getHotelID());

        Manager newManager = new Manager(UUID.randomUUID(), "Jane", "Smith", "jane.smith@example.com", "0987654321", null);
        input.setManagerList(List.of(newManager));
        assertEquals(List.of(newManager), input.getManagerList());

        Service newService = new Service(UUID.randomUUID(), "Wi-Fi", new BigDecimal("15.00"));
        input.setServiceList(List.of(newService));
        assertEquals(List.of(newService), input.getServiceList());
    }

    @Test
    public void testToString() {
        UUID hotelID = UUID.randomUUID();

        UUID managerId = UUID.randomUUID();
        Manager manager = new Manager(managerId, "John", "Doe", "chris.georgiev@example.com", "1234567890", null);

        UUID serviceId = UUID.randomUUID();
        Service service = new Service(serviceId, "Room Cleaning", new BigDecimal("25.00"));

        List<Manager> managerList = List.of(manager);
        List<Service> serviceList = List.of(service);

        UpdateHotelInput input = new UpdateHotelInput(managerList, serviceList, hotelID);

        String toStringOutput = input.toString();
        assertTrue(toStringOutput.contains("managerList=" + managerList));
        assertTrue(toStringOutput.contains("serviceList=" + serviceList));
        assertTrue(toStringOutput.contains("hotelID=" + hotelID));
    }

    @Test
    public void testBuilder() {
        UUID hotelID = UUID.randomUUID();

        UUID managerId = UUID.randomUUID();
        Manager manager = new Manager(managerId, "John", "Doe", "chris.georgiev@example.com", "1234567890", null);

        UUID serviceId = UUID.randomUUID();
        Service service = new Service(serviceId, "Room Cleaning", new BigDecimal("25.00"));

        List<Manager> managerList = List.of(manager);
        List<Service> serviceList = List.of(service);

        UpdateHotelInput input = UpdateHotelInput.builder()
                .managerList(managerList)
                .serviceList(serviceList)
                .hotelID(hotelID)
                .build();

        assertEquals(managerList, input.getManagerList());
        assertEquals(serviceList, input.getServiceList());
        assertEquals(hotelID, input.getHotelID());
    }

    @Test
    public void testNoArgsConstructor() {
        UpdateHotelInput input = new UpdateHotelInput();
        assertNull(input.getManagerList());
        assertNull(input.getServiceList());
        assertNull(input.getHotelID());
    }
}
