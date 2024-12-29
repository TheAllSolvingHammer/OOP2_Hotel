package com.tuvarna.hotel.api.models;

import com.tuvarna.hotel.api.enums.TypeRoom;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class Room {
    private UUID roomID;
    private Integer floor;
    private String roomNumber;
    private BigDecimal price;
    private TypeRoom roomType;

    @Override
    public String toString() {
        return roomNumber+" - "+floor+" - "+price+" - "+roomType;
    }
}
