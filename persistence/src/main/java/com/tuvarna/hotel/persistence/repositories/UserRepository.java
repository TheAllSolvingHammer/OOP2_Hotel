package com.tuvarna.hotel.persistence.repositories;

import com.tuvarna.hotel.persistence.contracts.EntityMarker;
import com.tuvarna.hotel.persistence.entities.UserEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository<T extends EntityMarker,E extends UUID> {
    Optional<T> findByUsername(String username);
    List<T> findAllOwners();
    List<T> findAllManagers();
    List<T> findAllReceptionist();
}
