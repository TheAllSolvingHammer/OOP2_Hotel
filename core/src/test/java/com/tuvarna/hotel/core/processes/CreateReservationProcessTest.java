package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.core.converters.ConvertServicesToEntity;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.ClientRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.RoomRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class CreateReservationProcessTest {

    @Mock
    private RoomRepositoryImpl roomRepositoryMock;

    @Mock
    private ReservationRepositoryImpl reservationRepositoryMock;

    @Mock
    private ClientRepositoryImpl clientRepositoryMock;

    @Mock
    private UserRepositoryImpl userRepositoryMock;

    @Mock
    private ConvertServicesToEntity converterMock;

    @InjectMocks
    private CreateReservationProcess createReservationProcess;

    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonManager> singletonManagerMock = mockStatic(SingletonManager.class)) {
            singletonManagerMock.when(() -> SingletonManager.getInstance(RoomRepositoryImpl.class)).thenReturn(roomRepositoryMock);
            singletonManagerMock.when(() -> SingletonManager.getInstance(ReservationRepositoryImpl.class)).thenReturn(reservationRepositoryMock);
            singletonManagerMock.when(() -> SingletonManager.getInstance(ClientRepositoryImpl.class)).thenReturn(clientRepositoryMock);
            singletonManagerMock.when(() -> SingletonManager.getInstance(UserRepositoryImpl.class)).thenReturn(userRepositoryMock);
            singletonManagerMock.when(() -> SingletonManager.getInstance(ConvertServicesToEntity.class)).thenReturn(converterMock);


        }
    }
}