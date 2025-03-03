package com.tuvarna.hotel.api.models.entities;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@Builder
@AllArgsConstructor
public class Reservations {
    private UUID reservationID;
    private String receptionistName;
}
