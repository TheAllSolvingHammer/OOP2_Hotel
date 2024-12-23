package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ConvertServicesToList implements BaseConverter<List<HotelEntity>,List<Hotel>>{

    @Override
    public List<Hotel> convert(List<HotelEntity> hotelEntities) {
        return hotelEntities.stream()
                .map(hotelEntity -> new Hotel(
                        hotelEntity.getId(),
                        hotelEntity.getName(),
                        hotelEntity.getLocation(),
                        hotelEntity.getRating(),
                        hotelEntity.getServiceList().stream().map(ServiceEntity::getServiceName).toList()
                ))
                .collect(Collectors.toList());
    }
}
