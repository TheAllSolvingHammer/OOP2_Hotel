package com.tuvarna.hotel.persistence.entities;

import com.tuvarna.hotel.persistence.contracts.EntityMarker;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@EqualsAndHashCode()
@Table(name="hotels")
@Entity
public class HotelEntity implements EntityMarker {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="hotel_id",updatable = false,nullable = false)
    private UUID id;

    @Column(name="hotel_name",nullable = false)
    private String name;

    //todo make the check if the address is really existing
    @Column(name="location",nullable = false)
    private String location;

    @Column(name="rating",nullable = false)
    private Integer rating;

    @ManyToMany
    @JoinTable(
            name="hotels_services",
            joinColumns = @JoinColumn(name="hotel_id"),
            inverseJoinColumns = @JoinColumn(name="service_id")
    )
    private List<ServiceEntity> serviceList;

    @ManyToMany
    @JoinTable(
            name = "hotel_users",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> hotelList;

//    @OneToMany(mappedBy = "hotel")
//    private RoomEntity room;
}
