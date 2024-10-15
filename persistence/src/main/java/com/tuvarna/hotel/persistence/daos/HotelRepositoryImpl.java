package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.repositories.HotelRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HotelRepositoryImpl implements HotelRepository<HotelEntity,UUID> {

    @Override
    public Optional<HotelEntity> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<HotelEntity> getAll() {
        return List.of();
    }

    @Override
    public HotelEntity save(HotelEntity hotelEntity) {
        return null;
    }

    @Override
    public HotelEntity delete(HotelEntity hotelEntity) {
        return null;
    }
}
