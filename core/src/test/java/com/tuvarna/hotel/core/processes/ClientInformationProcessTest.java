package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.models.query.client.information.ClientInformationOperation;
import com.tuvarna.hotel.core.converters.ConvertEntityToClient;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.ClientRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class ClientInformationProcessTest {

    @Mock
    private ClientRepositoryImpl clientRepositoryMock;

    @Mock
    private ConvertEntityToClient converterMock;


    @InjectMocks
    private ClientInformationProcess clientInformationProcess=new ClientInformationProcess();


    @Test
    public void testProcess_validInput_returnsClientInformationOutput2() throws Exception {
        try (MockedStatic<SingletonManager> singletonManagerMock = mockStatic(SingletonManager.class)) {

            singletonManagerMock.when(() -> SingletonManager.getInstance(ClientRepositoryImpl.class)).thenReturn(clientRepositoryMock);
            singletonManagerMock.when(() -> SingletonManager.getInstance(ConvertEntityToClient.class)).thenReturn(converterMock);

        }
    }

    @Test
    void getInstance() {
        assertInstanceOf(BaseProcessor.class, clientInformationProcess);
        assertInstanceOf(ClientInformationOperation.class, clientInformationProcess);
    }
    @Test
    void isAnnotationPresent() {
        boolean hasAnnotation = this.clientInformationProcess.getClass().isAnnotationPresent(Singleton.class);
        assertTrue(hasAnnotation);
    }

}