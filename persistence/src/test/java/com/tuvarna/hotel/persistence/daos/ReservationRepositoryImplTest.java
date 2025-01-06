package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.repositories.BaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

class ReservationRepositoryImplTest {

    private  ReservationRepositoryImpl reservationRepository;

    @BeforeEach
     void setUp() {
        reservationRepository= SingletonManager.getInstance(ReservationRepositoryImpl.class);
    }
    @Test
    void getInstance(){
        assertInstanceOf(ReservationRepositoryImpl.class, reservationRepository);
        assertInstanceOf(BaseRepository.class, reservationRepository);
    }
    @Test
    void TestSingleton(){
        ReservationRepositoryImpl instance1 = SingletonManager.getInstance(ReservationRepositoryImpl.class);
        assertSame(instance1, reservationRepository);
    }
}