package com.tuvarna.hotel.api.models.entities;

import lombok.*;

import java.util.List;
import java.util.UUID;
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class Manager {
    @EqualsAndHashCode.Include
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<Hotel> hotelList;

    @Override
    public String toString() {
        return firstName+" "+lastName;
    }

}
