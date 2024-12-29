package com.tuvarna.hotel.api.models.create.client;

import com.tuvarna.hotel.api.base.OperationInput;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
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
    @Size(min = 10,max=14,message = "Phone number must be between 10 and 14 characters")
    private String phone;
    @Size(min = 10,max = 10,message = "UCN must be 10 characters")
    private String ucn;
    private String address;
    @Email(message = "Incorrect email")
    private String email;
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;
    @Past(message = "Issue date must be in the past")
    private LocalDate issueDate;
    @Future(message ="Expire date must be in the future")
    private LocalDate expireDate;
    private String issuedBy;
}
