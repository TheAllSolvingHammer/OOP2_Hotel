package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.core.converters.ConvertServicesToEntity;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class CreateRoomProcessTest {
    @Mock
    private RoomRepositoryImpl roomRepositoryMock;

    @Mock
    private HotelRepositoryImpl hotelRepositoryMock;

    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonManager> singletonManagerMock = mockStatic(SingletonManager.class)) {
            singletonManagerMock.when(() -> SingletonManager.getInstance(RoomRepositoryImpl.class)).thenReturn(roomRepositoryMock);
            singletonManagerMock.when(() -> SingletonManager.getInstance(HotelRepositoryImpl.class)).thenReturn(hotelRepositoryMock);

        }
    }
}