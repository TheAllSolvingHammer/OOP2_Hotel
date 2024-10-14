package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.entities.RoomEntity;
import com.tuvarna.hotel.persistence.repositories.RoomRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;

import java.util.UUID;
@RequiredArgsConstructor
public class RoomRepositoryImpl extends BaseRepositoryImpl<RoomEntity, UUID> implements RoomRepository {
    public RoomRepositoryImpl(SessionFactory sessionFactory, Class<RoomEntity> entityClass) {
        super(sessionFactory, entityClass);
    }
}
