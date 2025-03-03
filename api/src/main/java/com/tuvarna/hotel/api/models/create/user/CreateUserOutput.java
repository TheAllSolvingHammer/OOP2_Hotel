package com.tuvarna.hotel.api.models.create.user;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateUserOutput implements OperationOutput {
    private String message;
}
