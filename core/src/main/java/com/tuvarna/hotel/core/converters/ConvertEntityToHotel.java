package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import com.tuvarna.hotel.api.models.display.service.Service;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ConvertEntityToHotel implements BaseConverter<List<HotelEntity>,List<Hotel>>{

    @Override
    public List<Hotel> convert(List<HotelEntity> hotelEntities) {
        return hotelEntities.stream()
                .map(hotelEntity -> Hotel.builder()
                        .id(hotelEntity.getId())
                        .name(hotelEntity.getName())
                        .location(hotelEntity.getLocation())
                        .stars(hotelEntity.getRating())
                        .serviceList(hotelEntity.getServiceList().stream()
                                .map(serviceEntity -> Service.builder()
                                        .id(serviceEntity.getId())
                                        .price(serviceEntity.getPrice())
                                        .name(serviceEntity.getServiceName())
                                        .build())
                                .collect(Collectors.toList()))
                        .userList(hotelEntity.getUserList().stream()
                                .map(UserEntity::getId)
                                .collect(Collectors.toList()))
                        .build()
                )
                .collect(Collectors.toList());
    }
}
