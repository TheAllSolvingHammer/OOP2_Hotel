package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.models.entities.Owner;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ConvertUsersToOwnerTest {

    private final ConvertUsersToOwner convertUsersToOwner = new ConvertUsersToOwner();

    @Test
    void testConvert() {
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .firstName("Alice")
                .lastName("Smith")
                .email("alice.smith@example.com")
                .phone("987654321")
                .hotelList(List.of(HotelEntity.builder()
                        .id(UUID.randomUUID())
                        .name("Hotel Royale")
                        .rating(4)
                        .serviceList(List.of(ServiceEntity.builder()
                                .id(UUID.randomUUID())
                                .serviceName("Breakfast")
                                .price(BigDecimal.valueOf(15.00))
                                .build()))
                        .build()))
                .build();

        List<Owner> owners = convertUsersToOwner.convert(List.of(userEntity));

        assertNotNull(owners);
        assertEquals(1, owners.size());
        Owner owner = owners.getFirst();
        assertEquals(userEntity.getId(), owner.getId());
        assertEquals(userEntity.getFirstName(), owner.getFirstName());
        assertEquals(userEntity.getLastName(), owner.getLastName());
        assertEquals(userEntity.getEmail(), owner.getEmail());
        assertEquals(userEntity.getPhone(), owner.getPhoneNumber());
        assertEquals(1, owner.getHotelList().size());
    }
}
