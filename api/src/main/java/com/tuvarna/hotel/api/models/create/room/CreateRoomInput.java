package com.tuvarna.hotel.api.models.create.room;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.enums.TypeRoom;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
    private Integer floor;
    @NotEmpty(message = "Room number can not be empty")
    @NotNull(message = "Room number can not be null")
    private String roomNumber;
    @NotNull(message = "Price is empty")
    private BigDecimal price;
    @NotNull(message = "Type is empty")
    private TypeRoom type;
    private UUID hotelID;
}
