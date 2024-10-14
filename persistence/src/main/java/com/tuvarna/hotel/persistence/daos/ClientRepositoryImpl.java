package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.entities.ClientEntity;
import com.tuvarna.hotel.persistence.repositories.ClientRepository;
import org.hibernate.SessionFactory;

import java.util.UUID;

public class ClientRepositoryImpl extends BaseRepositoryImpl<ClientEntity, UUID> implements ClientRepository {
    public ClientRepositoryImpl(SessionFactory sessionFactory, Class<ClientEntity> entityClass) {
        super(sessionFactory, entityClass);
    }
}
