package com.tuvarna.hotel.api.models.display.manager;

import com.tuvarna.hotel.api.models.display.hotel.Hotel;
import lombok.*;

import java.util.List;
import java.util.UUID;
@EqualsAndHashCode
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
