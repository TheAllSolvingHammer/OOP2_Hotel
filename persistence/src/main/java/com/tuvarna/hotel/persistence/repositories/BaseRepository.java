package com.tuvarna.hotel.persistence.repositories;

import com.tuvarna.hotel.persistence.contracts.EntityMarker;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseRepository<T extends EntityMarker,E extends UUID> {
    Optional<T> findById(E id);

    List<T> getAll();

    void save(T t);

    T delete(T t);

    boolean existsById(E id);
}
