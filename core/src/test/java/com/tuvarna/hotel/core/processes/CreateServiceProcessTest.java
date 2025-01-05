package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.core.converters.ConvertServicesToEntity;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class CreateServiceProcessTest {
    @Mock
    private ServiceRepositoryImpl serviceRepository;

    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonManager> singletonManagerMock = mockStatic(SingletonManager.class)) {
            singletonManagerMock.when(() -> SingletonManager.getInstance(ServiceRepositoryImpl.class)).thenReturn(ServiceRepositoryImpl.class);

        }
    }

}