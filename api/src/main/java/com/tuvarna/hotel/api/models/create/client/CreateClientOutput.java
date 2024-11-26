package com.tuvarna.hotel.api.models.create.client;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateClientOutput implements OperationOutput {
    private String message;
}


