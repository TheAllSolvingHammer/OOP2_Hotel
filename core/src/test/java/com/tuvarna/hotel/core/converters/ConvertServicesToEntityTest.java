package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.models.entities.Service;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConvertServicesToEntityTest {

    private final ConvertServicesToEntity convertServicesToEntity = new ConvertServicesToEntity();

    @Test
    void testConvert() {
        Service service = Service.builder()
                .id(UUID.randomUUID())
                .name("WiFi")
                .price(BigDecimal.valueOf(10.50))
                .build();

        List<ServiceEntity> serviceEntities = convertServicesToEntity.convert(List.of(service));

        assertNotNull(serviceEntities);
        assertEquals(1, serviceEntities.size());
        ServiceEntity serviceEntity = serviceEntities.getFirst();
        assertEquals(service.getId(), serviceEntity.getId());
        assertEquals(service.getName(), serviceEntity.getServiceName());
        assertEquals(service.getPrice(), serviceEntity.getPrice());
    }
}
