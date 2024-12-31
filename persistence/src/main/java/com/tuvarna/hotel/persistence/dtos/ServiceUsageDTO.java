package com.tuvarna.hotel.persistence.dtos;

import lombok.*;


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
}
