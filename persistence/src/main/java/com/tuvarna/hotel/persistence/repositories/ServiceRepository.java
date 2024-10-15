package com.tuvarna.hotel.persistence.repositories;

import com.tuvarna.hotel.persistence.contracts.EntityMarker;

import java.util.UUID;

public interface ServiceRepository<T extends EntityMarker,E extends UUID> extends BaseRepository<T,E> {
}
