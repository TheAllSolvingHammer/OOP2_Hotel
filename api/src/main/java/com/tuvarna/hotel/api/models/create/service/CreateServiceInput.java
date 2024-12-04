package com.tuvarna.hotel.api.models.create.service;

import com.tuvarna.hotel.api.base.OperationInput;
import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateServiceInput implements OperationInput {
    private UUID id;
    private String serviceName;
    private BigDecimal price;
}
