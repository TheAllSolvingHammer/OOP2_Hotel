package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.repositories.HotelRepository;
import org.hibernate.SessionFactory;

import java.util.UUID;

public class HotelRepositoryImpl extends BaseRepositoryImpl<HotelEntity, UUID> implements HotelRepository {
    public HotelRepositoryImpl(SessionFactory sessionFactory, Class<HotelEntity> entityClass) {
        super(sessionFactory, entityClass);
    }
}
