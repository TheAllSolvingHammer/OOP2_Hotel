package com.tuvarna.hotel.persistence.entities;

import com.tuvarna.hotel.persistence.contracts.EntityMarker;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@EqualsAndHashCode()
@Entity
@Table(name="services")
public class ServiceEntity implements EntityMarker {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="service_id",nullable = false,updatable = false)
    private UUID id;

    @Column(name="service_name",nullable = false)
    private String serviceName;

    @Column(name="price",nullable = false)
    private BigDecimal price;
}
