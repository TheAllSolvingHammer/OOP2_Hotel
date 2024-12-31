package com.tuvarna.hotel.api.models.entities;

import com.tuvarna.hotel.api.enums.RatingClient;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Client {
    @EqualsAndHashCode.Include
    private UUID id;
    private String firstName;
    private String lastName;
    private String phone;
    private String ucn;
    private String address;
    private String email;
    private LocalDate birthDate;
    private LocalDate issueDate;
    private LocalDate expireDate;
    private String issuedBy;
    private RatingClient rating;

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
