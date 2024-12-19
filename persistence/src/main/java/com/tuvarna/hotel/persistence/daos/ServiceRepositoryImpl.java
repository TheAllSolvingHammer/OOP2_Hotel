package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import com.tuvarna.hotel.persistence.repositories.ServiceRepository;
import java.util.UUID;

@Singleton
public class ServiceRepositoryImpl extends BaseRepositoryImpl<ServiceEntity,UUID> implements ServiceRepository<ServiceEntity,UUID> {

    public ServiceRepositoryImpl() {
        super(ServiceEntity.class);
    }
}
