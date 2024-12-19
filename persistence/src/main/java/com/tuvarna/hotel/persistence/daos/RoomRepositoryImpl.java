package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.entities.RoomEntity;
import com.tuvarna.hotel.persistence.repositories.RoomRepository;
import java.util.UUID;

@Singleton
public class RoomRepositoryImpl extends BaseRepositoryImpl<RoomEntity,UUID> implements RoomRepository<RoomEntity,UUID> {


    public RoomRepositoryImpl() {
        super(RoomEntity.class);
    }
}
