package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import com.tuvarna.hotel.api.models.display.manager.Manager;
import com.tuvarna.hotel.api.models.display.owner.Owner;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class ConvertUsersToManager implements BaseConverter<List<UserEntity>, List<Manager>> {

    @Override
    public List<Manager> convert(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(userEntity -> Manager.builder()
                        .id(userEntity.getId())
                        .firstName(userEntity.getFirstName())
                        .lastName(userEntity.getLastName())
                        .email(userEntity.getEmail())
                        .phoneNumber(userEntity.getPhone())
                        .hotelList(userEntity.getHotelList()
                                .stream()
                                .map(hotelEntity ->Hotel.builder()
                                        .id(hotelEntity.getId())
                                        .name(hotelEntity.getName())
                                        .stars(hotelEntity.getRating())
                                        .build()).toList())
                        .build()).toList();



    }
}
