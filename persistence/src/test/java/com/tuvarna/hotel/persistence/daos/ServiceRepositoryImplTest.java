package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.repositories.BaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceRepositoryImplTest {
    private  ServiceRepositoryImpl serviceRepository;

    @BeforeEach
    void setUp() {
        serviceRepository = SingletonManager.getInstance(ServiceRepositoryImpl.class);
    }
    @Test
    void getInstance(){
        assertInstanceOf(ServiceRepositoryImpl.class, serviceRepository);
        assertInstanceOf(BaseRepository.class, serviceRepository);
    }
    @Test
    void TestSingleton(){
        ServiceRepositoryImpl instance1 = SingletonManager.getInstance(ServiceRepositoryImpl.class);
        assertSame(instance1, serviceRepository);
    }
}