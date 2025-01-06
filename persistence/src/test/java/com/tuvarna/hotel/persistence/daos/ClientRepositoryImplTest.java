package com.tuvarna.hotel.persistence.daos;

import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.repositories.BaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientRepositoryImplTest {
    private  ClientRepositoryImpl clientRepository;

    @BeforeEach
    void setUp() {
        clientRepository= SingletonManager.getInstance(ClientRepositoryImpl.class);
    }
    @Test
    void getInstance(){
        assertInstanceOf(ClientRepositoryImpl.class, clientRepository);
        assertInstanceOf(BaseRepository.class, clientRepository);
    }
    @Test
    void TestSingleton(){
        ClientRepositoryImpl instance1 = SingletonManager.getInstance(ClientRepositoryImpl.class);
        assertSame(instance1, clientRepository);
    }
}