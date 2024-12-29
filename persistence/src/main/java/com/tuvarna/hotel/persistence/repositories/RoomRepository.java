package com.tuvarna.hotel.persistence.repositories;

import com.tuvarna.hotel.persistence.contracts.EntityMarker;
import com.tuvarna.hotel.persistence.entities.HotelEntity;

import java.util.List;
import java.util.UUID;

public interface RoomRepository<T extends EntityMarker,E extends UUID>{
    List<T> findAllByHotel(HotelEntity hotelEntity);
}

