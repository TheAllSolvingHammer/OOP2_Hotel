package com.tuvarna.hotel.api.models.display.hotel;

import com.tuvarna.hotel.api.models.display.service.Service;
import lombok.*;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor()
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Hotel {
    private UUID id;
    @EqualsAndHashCode.Exclude
    private String name;
    @EqualsAndHashCode.Exclude
    private String location;
    @EqualsAndHashCode.Exclude
    private Integer stars;
    @EqualsAndHashCode.Exclude
    private List<Service> serviceList;
    @EqualsAndHashCode.Exclude
    private List<UUID> userList;


//    private List<Owner> ownerList;
    @Override
    public String toString() {
        return name;
    }
}
