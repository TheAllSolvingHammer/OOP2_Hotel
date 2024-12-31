package com.tuvarna.hotel.api.models.update.client;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateClientOutput implements OperationOutput{
    private String message;
}
