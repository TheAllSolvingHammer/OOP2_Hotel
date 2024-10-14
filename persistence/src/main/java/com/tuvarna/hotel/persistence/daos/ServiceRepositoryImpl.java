package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import com.tuvarna.hotel.persistence.repositories.ServiceRepository;
import org.hibernate.SessionFactory;

import java.util.UUID;

public class ServiceRepositoryImpl extends BaseRepositoryImpl<ServiceEntity, UUID> implements ServiceRepository {
    public ServiceRepositoryImpl(SessionFactory sessionFactory, Class<ServiceEntity> entityClass) {
        super(sessionFactory, entityClass);
    }
}
