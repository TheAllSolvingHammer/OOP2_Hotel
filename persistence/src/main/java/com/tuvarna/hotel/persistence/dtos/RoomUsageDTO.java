package com.tuvarna.hotel.persistence.dtos;

import com.tuvarna.hotel.persistence.enums.RoomType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@EqualsAndHashCode()
public class RoomUsageDTO {
    private String roomNumber;
    private BigDecimal price;
    private RoomType roomType;
    private Long usageCount;
}
