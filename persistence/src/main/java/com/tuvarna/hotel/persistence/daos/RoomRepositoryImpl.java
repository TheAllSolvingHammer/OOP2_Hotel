package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.entities.RoomEntity;
import com.tuvarna.hotel.persistence.repositories.RoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
public class RoomRepositoryImpl implements RoomRepository<RoomEntity,UUID> {

    @Override
    public Optional<RoomEntity> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<RoomEntity> getAll() {
        return List.of();
    }

    @Override
    public RoomEntity save(RoomEntity roomEntity) {
        return null;
    }

    @Override
    public RoomEntity delete(RoomEntity roomEntity) {
        return null;
    }
}
