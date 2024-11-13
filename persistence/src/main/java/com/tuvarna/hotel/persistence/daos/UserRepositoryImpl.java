package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl  implements UserRepository<UserEntity,UUID> {

    @Override
    public Optional<UserEntity> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<UserEntity> getAll() {
        return List.of();
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity delete(UserEntity userEntity) {
        return null;
    }
}
