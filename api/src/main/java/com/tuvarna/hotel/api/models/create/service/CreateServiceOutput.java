package com.tuvarna.hotel.api.models.create.service;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateServiceOutput implements OperationOutput {
    private String message;
}
