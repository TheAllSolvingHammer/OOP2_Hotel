package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.core.converters.ConvertServicesToEntity;
import com.tuvarna.hotel.core.converters.ConvertUsersToReceptionist;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.ClientRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.RoomRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;

class DisplayAllEmployeeProcessorTest {

    @Mock
    private UserRepositoryImpl userRepository;

    @Mock
    private ConvertUsersToReceptionist converter;

    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonManager> singletonManagerMock = mockStatic(SingletonManager.class)) {
            singletonManagerMock.when(() -> SingletonManager.getInstance(UserRepositoryImpl.class)).thenReturn(userRepository);
            singletonManagerMock.when(() -> SingletonManager.getInstance(ConvertUsersToReceptionist.class)).thenReturn(converter);

        }
    }
}