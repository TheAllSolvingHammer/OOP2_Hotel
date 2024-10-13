package com.tuvarna.hotel.persistence.entities;

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
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="service_id",nullable = false,updatable = false)
    private UUID id;

    @Column(name="service_name",nullable = false)
    private String serviceName;

    @Column(name="price",nullable = false)
    private BigDecimal price;
}
