package com.tuvarna.hotel.api.models.create.service;

import com.tuvarna.hotel.api.base.OperationInput;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateServiceInput implements OperationInput {
    @NotEmpty(message="Service name can not be empty")
    private String serviceName;
    private BigDecimal price;
}
