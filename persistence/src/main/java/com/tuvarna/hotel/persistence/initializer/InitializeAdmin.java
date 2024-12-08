package com.tuvarna.hotel.persistence.initializer;

import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.enums.RoleEntity;

public class InitializeAdmin {
    private static UserRepositoryImpl userRepository = new UserRepositoryImpl();
    public static void addAdmin(){
        UserEntity userEntity = UserEntity.builder()
                .email("example@gmail.com")
                .phone("2378382")
                .hashedPassword("111111")
                .firstName("Yordan")
                .lastName("Simeonov")
                .role(RoleEntity.ADMINISTRATOR)
                .username("dancho")
                .build();
        userRepository.save(userEntity);
    }
}
