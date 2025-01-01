package com.tuvarna.hotel.api.models.query.reservation;

import com.tuvarna.hotel.api.base.OperationInput;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class QueryServicesInput implements OperationInput {
    private UUID hotelId;
    private LocalDate startDate;
    private LocalDate endDate;
}
