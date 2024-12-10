package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.repositories.HotelRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HotelRepositoryImpl extends BaseRepositoryImpl<HotelEntity,UUID> implements HotelRepository<HotelEntity,UUID> {

    public HotelRepositoryImpl() {
        super(HotelEntity.class);
    }
}
