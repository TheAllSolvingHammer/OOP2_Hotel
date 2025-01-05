package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.models.entities.Service;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ConvertServicesTest {

    private final ConvertServices convertServices = new ConvertServices();

    @Test
    void testConvert() {
        ServiceEntity serviceEntity = ServiceEntity.builder()
                .id(UUID.randomUUID())
                .serviceName("WiFi")
                .price(BigDecimal.valueOf(10.50))
                .build();

        List<Service> services = convertServices.convert(List.of(serviceEntity));

        assertNotNull(services);
        assertEquals(1, services.size());
        Service service = services.getFirst();
        assertEquals(serviceEntity.getId(), service.getId());
        assertEquals(serviceEntity.getServiceName(), service.getName());
        assertEquals(serviceEntity.getPrice(), service.getPrice());
    }
}
