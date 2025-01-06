package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.models.entities.Receptionist;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.tuvarna.hotel.persistence.entities.ServiceEntity.*;
import static org.junit.jupiter.api.Assertions.*;

class ConvertUsersToReceptionistTest {

    private final ConvertUsersToReceptionist convertUsersToReceptionist = new ConvertUsersToReceptionist();

    @Test
    void testConvert() {
        UserEntity userEntity = UserEntity.builder()
                .id(UUID.randomUUID())
                .firstName("Bob")
                .lastName("Brown")
                .email("bob.brown@example.com")
                .phone("555555555")
                .hotelList(List.of(HotelEntity.builder()
                        .id(UUID.randomUUID())
                        .name("Hotel Grand")
                        .rating(3)
                        .location("Downtown")
                        .serviceList(List.of(builder()
                                .id(UUID.randomUUID())
                                .serviceName("Pool Access")
                                .price(BigDecimal.valueOf(20.00))
                                .build()))
                        .build()))
                .build();

        List<Receptionist> receptionists = convertUsersToReceptionist.convert(List.of(userEntity));

        assertNotNull(receptionists);
        assertEquals(1, receptionists.size());
        Receptionist receptionist = receptionists.getFirst();
        assertEquals(userEntity.getId(), receptionist.getId());
        assertEquals(userEntity.getFirstName(), receptionist.getFirstName());
        assertEquals(userEntity.getLastName(), receptionist.getLastName());
        assertEquals(userEntity.getEmail(), receptionist.getEmail());
        assertEquals(userEntity.getPhone(), receptionist.getPhoneNumber());
        assertEquals(1, receptionist.getHotelList().size());
    }
}
