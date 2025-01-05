package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.core.converters.ConvertEntityToHotel;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.RoomRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import jakarta.persistence.ManyToOne;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class GetAllHotelsEmployeeProcessTest {
    @Mock
    private UserRepositoryImpl userRepository;
    @Mock
    private HotelRepositoryImpl hotelRepository;
    @Mock
    private ConvertEntityToHotel converter;
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonManager> singletonManagerMock = mockStatic(SingletonManager.class)) {
            singletonManagerMock.when(() -> SingletonManager.getInstance(ReservationRepositoryImpl.class)).thenReturn(converter);
            singletonManagerMock.when(() -> SingletonManager.getInstance(UserRepositoryImpl.class)).thenReturn(userRepository);
            singletonManagerMock.when(() -> SingletonManager.getInstance(HotelRepositoryImpl.class)).thenReturn(hotelRepository);
        }
    }
}