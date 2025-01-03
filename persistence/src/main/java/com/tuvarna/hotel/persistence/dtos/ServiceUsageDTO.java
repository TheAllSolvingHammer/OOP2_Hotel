package com.tuvarna.hotel.persistence.dtos;

import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@EqualsAndHashCode()
public class ServiceUsageDTO {
    private String serviceName;
    private Long usageCount;
    private BigDecimal totalRevenue;
}
