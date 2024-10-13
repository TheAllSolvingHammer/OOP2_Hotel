package com.tuvarna.hotel.persistence.entities;

import com.tuvarna.hotel.persistence.enums.RoleEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
@EqualsAndHashCode()
@Entity
@Table(name="users")
public class UserEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "user_id",updatable = false,nullable = false)
   private UUID id;

   @Column(name="firstname",nullable = false)
   private String firstName;

   @Column(name="lastname",nullable = false)
   private String lastName;

   @Column(name="phone",nullable = false)
   private String phone;

   @Column(name="username",nullable = false)
   private String username;

   @Enumerated(EnumType.STRING)
   @Column(name="user_role")
   private RoleEntity role;

   @Column(name="email",nullable = false)
   private String email;

   @Column(name="password",nullable = false)
   private String hashedPassword;

   //todo users_to_hotel bidirectional relationship

}
