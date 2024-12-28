package com.tuvarna.hotel.api.models.update.service;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class UpdateHotelOutput implements OperationOutput {
    private String message;
}
