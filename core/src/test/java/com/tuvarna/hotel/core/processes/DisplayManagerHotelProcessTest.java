package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.core.converters.ConvertEntityToHotel;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;

class DisplayManagerHotelProcessTest {

    @Mock
    private HotelRepositoryImpl hotelRepository;

    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private ConvertEntityToHotel convertEntityToHotel;
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonManager> singletonManagerMock = mockStatic(SingletonManager.class)) {
            singletonManagerMock.when(() -> SingletonManager.getInstance(HotelRepositoryImpl.class)).thenReturn(hotelRepository);
            singletonManagerMock.when(() -> SingletonManager.getInstance(UserRepositoryImpl.class)).thenReturn(userRepository);
            singletonManagerMock.when(() -> SingletonManager.getInstance(ConvertEntityToHotel.class)).thenReturn(convertEntityToHotel);

        }
    }

}