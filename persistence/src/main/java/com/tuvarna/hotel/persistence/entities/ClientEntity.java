package com.tuvarna.hotel.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@EqualsAndHashCode()
@Table(name="clients")
@Entity
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_id",nullable = false,updatable = false)
    private UUID id;

    @Column(name="first_name",nullable = false)
    private String firstName;

    @Column(name="last_name",nullable = false)
    private String lastName;

    @Column(name="phone",nullable = false)
    private String phone;

    @Column(name="ucn",nullable = false)
    private String ucn;

    @Column(name="address",nullable = false)
    private String address;

    @Column(name="email",nullable = false)
    private String email;

    @Column(name="birth_date",nullable = false)
    private LocalDate birthDate;

    @Column(name="issue_date",nullable = false)
    private LocalDate issueDate;

    @Column(name = "expire_date",nullable = false)
    private LocalDate expireDate;

    @Column(name="issued_by",nullable = false)
    private String issuedBy;
}
