package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import com.tuvarna.hotel.api.models.display.owner.Owner;
import com.tuvarna.hotel.api.models.display.service.Service;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;
@Singleton
public class ConvertUsersToOwner implements BaseConverter<List<UserEntity>, List<Owner>> {

    @Override
    public List<Owner> convert(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(userEntity -> Owner.builder()
                        .id(userEntity.getId())
                        .firstName(userEntity.getFirstName())
                        .lastName(userEntity.getLastName())
                        .email(userEntity.getEmail())
                        .phoneNumber(userEntity.getPhone())
                        .hotelList(userEntity.getHotelList().stream()
                                .map(hotelEntity -> Hotel.builder()
                                        .id(hotelEntity.getId())
                                        .name(hotelEntity.getName())
                                        .location(hotelEntity.getLocation())
                                        .stars(hotelEntity.getRating())
                                        .serviceList(hotelEntity.getServiceList().stream()
                                                .map(serviceEntity -> Service.builder()
                                                        .name(serviceEntity.getServiceName())
                                                        .price(serviceEntity.getPrice())
                                                        .id(serviceEntity.getId())
                                                        .build())
                                                .collect(Collectors.toList())
                                        )
                                        .build()
                                )
                                .collect(Collectors.toList())
                        )
                        .build()
                )
                .collect(Collectors.toList());
    }
}
