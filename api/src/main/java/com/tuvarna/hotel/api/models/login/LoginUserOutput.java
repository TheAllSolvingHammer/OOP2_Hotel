package com.tuvarna.hotel.api.models.login;

import com.tuvarna.hotel.api.base.OperationOutput;
import com.tuvarna.hotel.api.enums.RoleType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class LoginUserOutput implements OperationOutput {
    private String message;
    private RoleType role;
}
