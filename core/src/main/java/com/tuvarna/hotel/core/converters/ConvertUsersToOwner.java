package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.models.display.owner.Owner;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.entities.UserEntity;

import java.util.List;
import java.util.stream.Collectors;
@Singleton
public class ConvertUsersToOwner implements BaseConverter<List<UserEntity>, List<Owner>> {

    @Override
    public List<Owner> convert(List<UserEntity> userEntities) {
        return userEntities.stream()
                .map(userEntity -> new Owner(
                        userEntity.getId(),
                        userEntity.getFirstName(),
                        userEntity.getLastName(),
                        userEntity.getEmail(),
                        userEntity.getPhone()
                ))
                .collect(Collectors.toList());
    }
}
