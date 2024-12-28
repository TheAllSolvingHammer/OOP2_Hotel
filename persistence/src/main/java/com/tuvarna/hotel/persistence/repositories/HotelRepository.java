package com.tuvarna.hotel.persistence.repositories;

import com.tuvarna.hotel.persistence.contracts.EntityMarker;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.enums.RoleEntity;
import jakarta.data.repository.Param;
import jakarta.data.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HotelRepository<T extends EntityMarker,E extends UUID>  {
    Optional<HotelEntity> findHotelById(UUID id);
    List<HotelEntity> findAllByOwner(UserEntity owner);

    List<UserEntity> findManagersWithHotels();
    List<HotelEntity> findAllHotelsWithUsers();
    List<HotelEntity> findAllByManager(UserEntity manager);
}
