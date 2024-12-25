package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.models.display.service.Service;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;

import java.util.List;
@Singleton
public class ConvertServicesToEntity implements BaseConverter<List<Service>, List<ServiceEntity>> {
    @Override
    public List<ServiceEntity> convert(List<Service> services) {
        return services.stream().map(service -> ServiceEntity.builder()
                .id(service.getId())
                .price(service.getPrice())
                .serviceName(service.getName())
                .build()).toList();
    }
}
