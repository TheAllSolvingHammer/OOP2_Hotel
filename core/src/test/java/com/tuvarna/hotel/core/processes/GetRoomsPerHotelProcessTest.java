package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.core.converters.ConvertEntityToRoom;
import com.tuvarna.hotel.core.converters.ConvertUsersToReceptionist;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.RoomRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class GetRoomsPerHotelProcessTest {
    @Mock
    private RoomRepositoryImpl roomRepository;
    @Mock
    private HotelRepositoryImpl hotelRepository;
    @Mock
    private ConvertEntityToRoom converter;
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonManager> singletonManagerMock = mockStatic(SingletonManager.class)) {
            singletonManagerMock.when(() -> SingletonManager.getInstance(ConvertEntityToRoom.class)).thenReturn(converter);
            singletonManagerMock.when(() -> SingletonManager.getInstance(HotelRepositoryImpl.class)).thenReturn(hotelRepository);
            singletonManagerMock.when(() -> SingletonManager.getInstance(RoomRepositoryImpl.class)).thenReturn(roomRepository);
        }
    }
}