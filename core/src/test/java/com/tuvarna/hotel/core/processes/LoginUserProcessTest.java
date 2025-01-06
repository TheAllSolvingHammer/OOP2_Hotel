package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.domain.encoder.MyPasswordEncoder;
import com.tuvarna.hotel.domain.encoder.PasswordEncoder;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.mockStatic;

class LoginUserProcessTest {
    @Mock
    private UserRepositoryImpl userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Test
    public void test() throws Exception {
        try (MockedStatic<SingletonManager> singletonManagerMock = mockStatic(SingletonManager.class)) {
            singletonManagerMock.when(() -> SingletonManager.getInstance(UserRepositoryImpl.class)).thenReturn(userRepository);
            singletonManagerMock.when(() -> SingletonManager.getInstance(MyPasswordEncoder.class)).thenReturn(passwordEncoder);}
    }
}