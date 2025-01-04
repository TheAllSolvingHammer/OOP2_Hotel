package com.tuvarna.hotel.api.models.entities;

import com.tuvarna.hotel.api.enums.TypeRoom;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@EqualsAndHashCode()
public class RoomQueryDTO {
    private String roomNumber;
    private BigDecimal price;
    private TypeRoom roomType;
    private Long usageCount;
}
