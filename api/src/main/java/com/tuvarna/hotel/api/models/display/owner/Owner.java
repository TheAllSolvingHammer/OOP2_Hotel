package com.tuvarna.hotel.api.models.display.owner;

import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class Owner {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private List<Hotel> hotelList;
}
