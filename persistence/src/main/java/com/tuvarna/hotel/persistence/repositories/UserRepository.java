package com.tuvarna.hotel.persistence.repositories;

import com.tuvarna.hotel.persistence.entities.UserEntity;

import java.util.UUID;

public interface UserRepository extends BaseRepository<UserEntity, UUID> {
}
