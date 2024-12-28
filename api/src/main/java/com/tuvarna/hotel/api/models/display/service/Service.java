package com.tuvarna.hotel.api.models.display.service;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Service {
    private UUID id;
    @EqualsAndHashCode.Exclude
    private String name;
    @EqualsAndHashCode.Exclude
    private BigDecimal price;

    @Override
    public String toString() {
        return name;
    }
}
