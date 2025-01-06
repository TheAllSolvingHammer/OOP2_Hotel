package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.core.converters.ConvertEntityToReservation;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;

class QueryReservationsProcessTest {
    @Mock
    private ReservationRepositoryImpl reservationRepository;
    @Mock
    private HotelRepositoryImpl hotelRepository;
    @Mock
    private ConvertEntityToReservation converter;
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonManager> singletonManagerMock = mockStatic(SingletonManager.class)) {
            singletonManagerMock.when(() -> SingletonManager.getInstance(ReservationRepositoryImpl.class)).thenReturn(reservationRepository);
            singletonManagerMock.when(() -> SingletonManager.getInstance(HotelRepositoryImpl.class)).thenReturn(hotelRepository);
            singletonManagerMock.when(() -> SingletonManager.getInstance(ConvertEntityToReservation.class)).thenReturn(converter);
        }
    }
}