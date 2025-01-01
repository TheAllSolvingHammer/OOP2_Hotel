package com.tuvarna.hotel.api.models.entities;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class ServicesDTO {
    private String serviceName;
    private Long usageCount;
    private BigDecimal totalRevenue;
}
