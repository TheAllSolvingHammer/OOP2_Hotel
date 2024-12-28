package com.tuvarna.hotel.api.models.create.client;

import com.tuvarna.hotel.api.base.OperationInput;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class CreateClientInput implements OperationInput {
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    private String lastName;
    private String phone;
    private String ucn;//unified citizen number=>egn
    private String address;
    private String email;
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    @Past(message = "Issue date must be in the past")
    private LocalDate issueDate;
    @Future(message ="Expire date must be in the future")
    private LocalDate expireDate;
    private String issuedBy;
}
