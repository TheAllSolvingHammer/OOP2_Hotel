package com.tuvarna.hotel.api.models.create.user;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.enums.RoleType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateUserInput implements OperationInput {
    @NotEmpty(message = "First name can not be null")
    private String firstName;
    @NotEmpty(message = "Last name can not be null")
    private String lastName;
    @Size(min = 10, max = 14, message = "Phone number must be between 10 and 14 characters")
    private String phone;
    @NotEmpty(message = "Username can not be null")
    private String username;
    private RoleType role;
    @NotEmpty(message="Email can not be empty")
    @Email(message = "Incorrect email")
    private String email;
    @NotEmpty(message = "Password can not be empty")
    private String password;
    @NotEmpty(message = "Password can not be empty")
    private String passwordSecond;
}
