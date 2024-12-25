package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.models.display.service.Service;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;

import java.util.List;

@Singleton
public class ConvertServices implements BaseConverter<List<ServiceEntity>,List<Service>> {
    @Override
    public List<Service> convert(List<ServiceEntity> serviceEntities) {
        return serviceEntities.stream().map(s -> Service.builder()
                .name(s.getServiceName())
                .id(s.getId())
                .price(s.getPrice())
                .build())
                .toList();
    }
}
