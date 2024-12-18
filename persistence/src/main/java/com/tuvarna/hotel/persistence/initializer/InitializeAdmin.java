package com.tuvarna.hotel.persistence.initializer;

import com.tuvarna.hotel.domain.encoder.MyPasswordEncoder;
import com.tuvarna.hotel.domain.encoder.PasswordEncoder;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.enums.RoleEntity;

public class InitializeAdmin {
    private static final UserRepositoryImpl userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
    public static void addAdmin(){
        PasswordEncoder passwordEncoder = SingletonManager.getInstance(MyPasswordEncoder.class);
        UserEntity userEntity = UserEntity.builder()
                .email("example@gmail.com")
                .phone("2378382")
                .hashedPassword(passwordEncoder.encode("111111"))
                .firstName("Yordan")
                .lastName("Simeonov")
                .role(RoleEntity.ADMINISTRATOR)
                .username("dancho")
                .build();
        userRepository.save(userEntity);
    }
}
