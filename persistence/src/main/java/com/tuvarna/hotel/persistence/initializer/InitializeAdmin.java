package com.tuvarna.hotel.persistence.initializer;

import com.tuvarna.hotel.domain.encoder.MyPasswordEncoder;
import com.tuvarna.hotel.domain.encoder.PasswordEncoder;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.enums.RoleEntity;
import org.apache.log4j.Logger;

public class InitializeAdmin {
    private static final UserRepositoryImpl userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
    private static final Logger log = Logger.getLogger(InitializeAdmin.class);
    public static void addAdmin(){
        PasswordEncoder passwordEncoder = SingletonManager.getInstance(MyPasswordEncoder.class);
        boolean adminExists = userRepository.findByUsername("dancho").isPresent();
        if (adminExists) {
            return;
        }
        log.info("Admin is missing, creating new one");
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
