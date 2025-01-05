package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class CreateHotelProcessTest {

    @Mock
    private HotelRepositoryImpl hotelRepositoryMock;

    @Mock
    private UserRepositoryImpl userRepositoryMock;


    @Test
    public void testProcess_validInput_returnsCreateHotelOutput() throws Exception {
        try (MockedStatic<SingletonManager> singletonManagerMock = mockStatic(SingletonManager.class)) {
            // Mock SingletonManager to return dependencies
            singletonManagerMock.when(() -> SingletonManager.getInstance(HotelRepositoryImpl.class)).thenReturn(hotelRepositoryMock);
            singletonManagerMock.when(() -> SingletonManager.getInstance(UserRepositoryImpl.class)).thenReturn(userRepositoryMock);
        }
    }

    }