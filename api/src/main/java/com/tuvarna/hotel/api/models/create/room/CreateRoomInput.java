package com.tuvarna.hotel.api.models.create.room;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.enums.TypeRoom;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateRoomInput implements OperationInput {
    private UUID id;
    private Integer floor;
    private String roomNumber;
    private BigDecimal price;
    private TypeRoom type;
    private UUID hotel_id;
}
