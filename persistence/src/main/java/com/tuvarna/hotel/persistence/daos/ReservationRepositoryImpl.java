package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.entities.ReservationEntity;
import com.tuvarna.hotel.persistence.repositories.ReservationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReservationRepositoryImpl implements ReservationRepository<ReservationEntity,UUID> {

    @Override
    public Optional<ReservationEntity> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<ReservationEntity> getAll() {
        return List.of();
    }

    @Override
    public ReservationEntity save(ReservationEntity reservationEntity) {
        return null;
    }

    @Override
    public ReservationEntity delete(ReservationEntity reservationEntity) {
        return null;
    }
}
