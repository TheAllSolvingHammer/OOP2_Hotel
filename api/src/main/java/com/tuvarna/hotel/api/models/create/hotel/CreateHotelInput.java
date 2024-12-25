package com.tuvarna.hotel.api.models.create.hotel;

import com.tuvarna.hotel.api.base.OperationInput;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateHotelInput implements OperationInput {
    @NotEmpty(message = "Hotel name can not be empty")
    private String name;
    @NotEmpty(message = "Location can not be empty")
    private String location;
    private Integer rating;
}
