package com.tuvarna.hotel.api.models.create.owner;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.enums.RoleType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateUserInput implements OperationInput {
    private UUID uuid;
    private String firstName;
    private String lastName;
    private String phone;
    private String username;
    private RoleType role;
    private String email;
    private String hashedPassword;
}
