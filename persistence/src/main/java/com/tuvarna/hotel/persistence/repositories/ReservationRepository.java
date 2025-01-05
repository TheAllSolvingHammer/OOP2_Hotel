package com.tuvarna.hotel.persistence.repositories;

import com.tuvarna.hotel.persistence.contracts.EntityMarker;
import com.tuvarna.hotel.persistence.dtos.ReceptionistReservationDTO;
import com.tuvarna.hotel.persistence.dtos.RoomUsageDTO;
import com.tuvarna.hotel.persistence.dtos.ServiceUsageDTO;
import com.tuvarna.hotel.persistence.entities.RoomEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository<T extends EntityMarker,E extends UUID> {
    Boolean isRoomAvailable(RoomEntity room, LocalDate startDate, LocalDate endDate);
    List<ServiceUsageDTO> countServiceUsage(UUID hotelId,LocalDate startDate, LocalDate endDate);
    List<ReceptionistReservationDTO> getReservationsByReceptionists(UUID hotelId, LocalDate startDate, LocalDate endDate);
    List<RoomUsageDTO> getRoomUsageByHotelAndDate(UUID hotelId, LocalDate startDate, LocalDate endDate);
    List<T> getReservationsByHotelAndDate(UUID hotelId, LocalDate startDate, LocalDate endDate);
    List<T> findAllByHotelId(UUID hotelId);
}
