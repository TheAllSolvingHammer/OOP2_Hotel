package com.tuvarna.hotel.persistence.repositories;

import com.tuvarna.hotel.persistence.contracts.EntityMarker;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.ReservationEntity;
import com.tuvarna.hotel.persistence.entities.RoomEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository<T extends EntityMarker,E extends UUID> {
    Boolean isRoomAvailable(RoomEntity room, LocalDate startDate, LocalDate endDate);
}
