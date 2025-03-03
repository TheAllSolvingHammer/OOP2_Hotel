package com.tuvarna.hotel.api.models.query.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.UUID;

public class QueryServicesInputTest {

    @Test
    public void testConstructorAndGettersSetters() {
        UUID hotelId = UUID.randomUUID();
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 10);

        QueryServicesInput input = new QueryServicesInput(hotelId, startDate, endDate);

        assertEquals(hotelId, input.getHotelId());
        assertEquals(startDate, input.getStartDate());
        assertEquals(endDate, input.getEndDate());

        UUID newHotelId = UUID.randomUUID();
        input.setHotelId(newHotelId);
        assertEquals(newHotelId, input.getHotelId());

        LocalDate newStartDate = LocalDate.of(2025, 2, 1);
        input.setStartDate(newStartDate);
        assertEquals(newStartDate, input.getStartDate());

        LocalDate newEndDate = LocalDate.of(2025, 2, 10);
        input.setEndDate(newEndDate);
        assertEquals(newEndDate, input.getEndDate());
    }

    @Test
    public void testToString() {
        UUID hotelId = UUID.randomUUID();
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 10);

        QueryServicesInput input = new QueryServicesInput(hotelId, startDate, endDate);

        String toStringOutput = input.toString();
        assertTrue(toStringOutput.contains("hotelId=" + hotelId));
        assertTrue(toStringOutput.contains("startDate=" + startDate));
        assertTrue(toStringOutput.contains("endDate=" + endDate));
    }

    @Test
    public void testBuilder() {
        UUID hotelId = UUID.randomUUID();
        LocalDate startDate = LocalDate.of(2025, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 10);

        QueryServicesInput input = QueryServicesInput.builder()
                .hotelId(hotelId)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        assertEquals(hotelId, input.getHotelId());
        assertEquals(startDate, input.getStartDate());
        assertEquals(endDate, input.getEndDate());
    }

    @Test
    public void testNoArgsConstructor() {
        QueryServicesInput input = new QueryServicesInput();
        assertNull(input.getHotelId());
        assertNull(input.getStartDate());
        assertNull(input.getEndDate());
    }
}
