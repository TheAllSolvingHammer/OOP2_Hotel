package com.tuvarna.hotel.api.models.create.client;

import com.tuvarna.hotel.api.base.OperationInput;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String firstName;
    private String lastName;
    private String phone;
    private String ucn;//unified citizen number=>egn
    private String address;
    private String email;
    private LocalDate birthDate;
    private LocalDate issueDate;
    private LocalDate expireDate;
    private String issuedBy;
}
