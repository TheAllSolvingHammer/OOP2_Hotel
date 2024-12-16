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
    @NotNull(message = "First name can not be null")
    private String firstName;
    @NotNull(message = "Last name can not be null")
    private String lastName;
    @NotNull(message = "Phone number is not correct")
    @Min(value=10,message = "Phone must be higher than 10 ")
    @Max(value=14,message = "Phone can not be higher than 14")
    private String phone;
    @NotNull(message = "Username can not be null")
    private String username;
    private RoleType role;
    @Email(message = "Incorrect email")
    private String email;
    private String password;
    private String passwordSecond;
}
