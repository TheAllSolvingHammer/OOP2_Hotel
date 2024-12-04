package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.persistence.contracts.EntityMarker;
import com.tuvarna.hotel.persistence.repositories.BaseRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public abstract class BaseRepositoryImpl<T extends EntityMarker,E extends UUID> implements BaseRepository<T,E> {

}
