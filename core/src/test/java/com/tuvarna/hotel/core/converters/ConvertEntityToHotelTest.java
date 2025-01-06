package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.entities.Service;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConvertEntityToHotelTest {

    private final ConvertEntityToHotel converter = new ConvertEntityToHotel();

    @Test
    void testConvert() {
        UUID serviceId = UUID.randomUUID();
        ServiceEntity serviceEntity = new ServiceEntity();
        serviceEntity.setId(serviceId);
        serviceEntity.setPrice(BigDecimal.valueOf(100.50));
        serviceEntity.setServiceName("Spa");


        UUID userId = UUID.randomUUID();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);

        UUID hotelId = UUID.randomUUID();
        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setId(hotelId);
        hotelEntity.setName("Luxury Stay");
        hotelEntity.setLocation("Paris");
        hotelEntity.setRating(5);
        hotelEntity.setServiceList(List.of(serviceEntity));
        hotelEntity.setUserList(List.of(userEntity));

        List<Hotel> hotels = converter.convert(List.of(hotelEntity));

        assertNotNull(hotels);
        assertEquals(1, hotels.size());

        Hotel hotel = hotels.getFirst();
        assertEquals(hotelId, hotel.getId());
        assertEquals("Luxury Stay", hotel.getName());
        assertEquals("Paris", hotel.getLocation());
        assertEquals(5, hotel.getStars());

        assertNotNull(hotel.getServiceList());
        assertEquals(1, hotel.getServiceList().size());
        Service service = hotel.getServiceList().getFirst();
        assertEquals(serviceId, service.getId());
        assertEquals(BigDecimal.valueOf(100.50), service.getPrice());
        assertEquals("Spa", service.getName());

        assertNotNull(hotel.getUserList());
        assertEquals(1, hotel.getUserList().size());
        assertEquals(userId, hotel.getUserList().getFirst());
    }
}
