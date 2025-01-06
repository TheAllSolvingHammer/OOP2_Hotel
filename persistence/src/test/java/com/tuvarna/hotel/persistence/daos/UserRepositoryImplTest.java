package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.repositories.BaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {
    private  UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
    }
    @Test
    void getInstance(){
        assertInstanceOf(UserRepositoryImpl.class, userRepository);
        assertInstanceOf(BaseRepository.class, userRepository);
    }
    @Test
    void TestSingleton(){
        UserRepositoryImpl instance1 = SingletonManager.getInstance(UserRepositoryImpl.class);
        assertSame(instance1, userRepository);
    }

}