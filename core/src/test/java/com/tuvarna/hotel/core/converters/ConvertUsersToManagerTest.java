package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.models.entities.Manager;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
class ConvertUsersToManagerTest {

    private final ConvertUsersToManager convertUsersToManager = new ConvertUsersToManager();

    @Test
    void testConvert() {
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phone("123456789")
                .hotelList(List.of(HotelEntity.builder()
                        .id(UUID.randomUUID())
                        .name("Hotel Paradise")
                        .rating(5)
                        .build()))
                .build();

        List<Manager> managers = convertUsersToManager.convert(List.of(userEntity));

        assertNotNull(managers);
        assertEquals(1, managers.size());
        Manager manager = managers.getFirst();
        assertEquals(userEntity.getId(), manager.getId());
        assertEquals(userEntity.getFirstName(), manager.getFirstName());
        assertEquals(userEntity.getLastName(), manager.getLastName());
        assertEquals(userEntity.getEmail(), manager.getEmail());
        assertEquals(userEntity.getPhone(), manager.getPhoneNumber());
        assertEquals(1, manager.getHotelList().size());
    }
}
