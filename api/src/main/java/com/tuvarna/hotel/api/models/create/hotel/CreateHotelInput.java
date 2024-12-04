package com.tuvarna.hotel.api.models.create.hotel;

import com.tuvarna.hotel.api.base.OperationInput;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateHotelInput implements OperationInput {
    private String name;
    private String location;
    private Integer rating;
    private List<String> services;
}
