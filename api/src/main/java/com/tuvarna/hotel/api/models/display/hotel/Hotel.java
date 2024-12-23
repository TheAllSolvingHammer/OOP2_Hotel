package com.tuvarna.hotel.api.models.display.hotel;

import lombok.*;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class Hotel {
    private UUID id;
    private String name;
    private String location;
    private Integer stars;
    private List<String> serviceList;
}
