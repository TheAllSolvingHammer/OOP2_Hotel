package com.tuvarna.hotel.api.models.update.service;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class UpdateServicesOutput implements OperationOutput {
    private String message;
}
