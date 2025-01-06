package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.repositories.BaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HotelRepositoryImplTest {
    private  HotelRepositoryImpl hotelRepository;

    @BeforeEach
    void setUp() {
        hotelRepository= SingletonManager.getInstance(HotelRepositoryImpl.class);
    }
    @Test
    void getInstance(){
        assertInstanceOf(HotelRepositoryImpl.class, hotelRepository);
        assertInstanceOf(BaseRepository.class, hotelRepository);
    }
    @Test
    void TestSingleton(){
        HotelRepositoryImpl instance1 = SingletonManager.getInstance(HotelRepositoryImpl.class);
        assertSame(instance1, hotelRepository);
    }
}