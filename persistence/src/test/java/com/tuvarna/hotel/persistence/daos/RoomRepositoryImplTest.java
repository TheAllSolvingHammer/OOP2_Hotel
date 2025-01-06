package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.repositories.BaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomRepositoryImplTest {
    private  RoomRepositoryImpl roomRepository;

    @BeforeEach
    void setUp() {
        roomRepository = SingletonManager.getInstance(RoomRepositoryImpl.class);
    }
    @Test
    void getInstance(){
        assertInstanceOf(RoomRepositoryImpl.class, roomRepository);
        assertInstanceOf(BaseRepository.class, roomRepository);
    }
    @Test
    void TestSingleton(){
        RoomRepositoryImpl instance1 = SingletonManager.getInstance(RoomRepositoryImpl.class);
        assertSame(instance1, roomRepository);
    }
}