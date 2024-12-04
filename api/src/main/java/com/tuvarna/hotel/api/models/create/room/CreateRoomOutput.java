package com.tuvarna.hotel.api.models.create.room;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateRoomOutput implements OperationOutput {
    private String message;
}
