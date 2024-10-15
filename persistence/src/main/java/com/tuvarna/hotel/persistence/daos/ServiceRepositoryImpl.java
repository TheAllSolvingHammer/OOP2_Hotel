package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import com.tuvarna.hotel.persistence.repositories.ServiceRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ServiceRepositoryImpl implements ServiceRepository<ServiceEntity,UUID> {
    @Override
    public Optional<ServiceEntity> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<ServiceEntity> getAll() {
        return List.of();
    }

    @Override
    public ServiceEntity save(ServiceEntity serviceEntity) {
        return null;
    }

    @Override
    public ServiceEntity delete(ServiceEntity serviceEntity) {
        return null;
    }
}
