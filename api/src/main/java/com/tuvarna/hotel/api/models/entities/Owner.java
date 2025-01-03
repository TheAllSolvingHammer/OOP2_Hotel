package com.tuvarna.hotel.api.models.entities;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Owner {
    @EqualsAndHashCode.Include
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<Hotel> hotelList;
}
