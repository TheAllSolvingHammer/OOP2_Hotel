package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.entities.ReservationEntity;
import com.tuvarna.hotel.persistence.repositories.ReservationRepository;
import org.hibernate.SessionFactory;

import java.util.UUID;

public class ReservationRepositoryImpl extends BaseRepositoryImpl<ReservationEntity, UUID> implements ReservationRepository {
    public ReservationRepositoryImpl(SessionFactory sessionFactory, Class<ReservationEntity> entityClass) {
        super(sessionFactory, entityClass);
    }
}
