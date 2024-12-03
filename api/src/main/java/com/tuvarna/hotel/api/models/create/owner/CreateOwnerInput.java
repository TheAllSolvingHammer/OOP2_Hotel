package com.tuvarna.hotel.api.models.create.owner;

import com.tuvarna.hotel.api.base.OperationInput;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public class CreateOwnerInput implements OperationInput {
    private String firstName;

    private String lastName;

    private String phone;

    private String username;

    //private RoleEntity role;

    private String email;

    private String hashedPassword;
}
