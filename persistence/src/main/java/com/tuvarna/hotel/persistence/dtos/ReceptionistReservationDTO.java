package com.tuvarna.hotel.persistence.dtos;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@EqualsAndHashCode()
public class ReceptionistReservationDTO {
    private UUID reservationID;
    private String receptionistName;
    private UUID hotelId;
}
