package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.entities.ClientEntity;
import com.tuvarna.hotel.persistence.repositories.ClientRepository;

import java.util.UUID;
@Singleton
public class ClientRepositoryImpl extends BaseRepositoryImpl<ClientEntity,UUID> implements ClientRepository<ClientEntity,UUID>{

    public ClientRepositoryImpl() {
        super(ClientEntity.class);
    }
}
