package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.entities.ClientEntity;
import com.tuvarna.hotel.persistence.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientRepositoryImpl  implements ClientRepository<ClientEntity,UUID>{


    @Override
    public Optional<ClientEntity> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<ClientEntity> getAll() {
        return List.of();
    }

    @Override
    public ClientEntity save(ClientEntity clientEntity) {
        return null;
    }

    @Override
    public ClientEntity delete(ClientEntity clientEntity) {
        return null;
    }
}
