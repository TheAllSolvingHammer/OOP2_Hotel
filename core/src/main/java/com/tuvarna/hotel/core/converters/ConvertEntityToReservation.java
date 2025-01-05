package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.enums.StatusReservation;
import com.tuvarna.hotel.api.enums.TypeReservation;
import com.tuvarna.hotel.api.models.entities.Reservation;
import com.tuvarna.hotel.api.models.entities.Service;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.entities.ReservationEntity;

import java.util.List;

@Singleton
public class ConvertEntityToReservation implements BaseConverter<List<ReservationEntity>,List<Reservation>>{
    @Override
    public List<Reservation> convert(List<ReservationEntity> reservationEntities) {
        return reservationEntities.stream().map(reservationEntity -> Reservation.builder()
                        .id(reservationEntity.getId())
                        .type(TypeReservation.getByCode(reservationEntity.getType().name()))
                        .startDate(reservationEntity.getStartDate())
                        .endDate(reservationEntity.getEndDate())
                        .price(reservationEntity.getPrice())
                        .room(reservationEntity.getRoom().getRoomNumber())
                        .hotel(reservationEntity.getRoom().getHotel().getName())
                        .client(reservationEntity.getClient().getFirstName()+" "+reservationEntity.getClient().getLastName())
                        .status(StatusReservation.getByCode(reservationEntity.getStatus().name()))
                        .services(reservationEntity.getServices().stream()
                                .map(service-> Service.builder()
                                        .price(service.getPrice())
                                        .name(service.getServiceName())
                                        .id(service.getId())
                                        .build())
                                .toList())
                        .build())
                .toList();
    }
}
