package com.tuvarna.hotel.api.models.query.reservation;

import com.tuvarna.hotel.api.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class QueryReservationInput implements OperationInput {
    @NotNull(message = "Hotel id can not be null")
    private UUID hotelId;
    @NotNull(message = "Start date can not be null")
    private LocalDate startDate;
    @NotNull(message = "End date can not be null")
    private LocalDate endDate;
}
