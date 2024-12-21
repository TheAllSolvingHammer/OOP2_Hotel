package com.tuvarna.hotel.core.converters;

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
                .map(userEntity -> new Manager(
                        userEntity.getId(),
                        userEntity.getFirstName(),
                        userEntity.getLastName(),
                        userEntity.getEmail(),
                        userEntity.getPhone(),
                        userEntity.getHotelList().stream().map(HotelEntity::getName).toList()
                ))
                .collect(Collectors.toList());
    }
}
