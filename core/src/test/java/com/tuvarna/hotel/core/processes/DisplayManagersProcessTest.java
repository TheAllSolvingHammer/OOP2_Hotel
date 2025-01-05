package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.core.converters.ConvertUsersToManager;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class DisplayManagersProcessTest {
    @Mock
    private UserRepositoryImpl userRepository;
    @Mock
    private ConvertUsersToManager convertUsersToManager;
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonManager> singletonManagerMock = mockStatic(SingletonManager.class)) {
            singletonManagerMock.when(() -> SingletonManager.getInstance(ConvertUsersToManager.class)).thenReturn(userRepository);
            singletonManagerMock.when(() -> SingletonManager.getInstance(UserRepositoryImpl.class)).thenReturn(convertUsersToManager);
        }
    }
}