package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.entities.Client;
import com.tuvarna.hotel.api.models.query.client.information.ClientInformationInput;
import com.tuvarna.hotel.api.models.query.client.information.ClientInformationOperation;
import com.tuvarna.hotel.api.models.query.client.information.ClientInformationOutput;
import com.tuvarna.hotel.core.converters.ConvertEntityToClient;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.ClientRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.ClientEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.List;

@Singleton
public class GetClientOutputProcess extends BaseProcessor implements ClientInformationOperation {
    private final ClientRepositoryImpl clientRepository = SingletonManager.getInstance(ClientRepositoryImpl.class);
    private final ConvertEntityToClient converter = SingletonManager.getInstance(ConvertEntityToClient.class);

    @Override
    public Either<ErrorProcessor, ClientInformationOutput> process(ClientInformationInput input) {
        return validateInput(input).flatMap(validInput ->Try.of(()-> {
            List<ClientEntity> clients =getClients(input);
            List<Client> clientList=converter.convert(clients);
            return ClientInformationOutput.builder()
                    .clientList(clientList)
                    .build();


        }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }

    private List<ClientEntity> getClients(ClientInformationInput input) {
        return clientRepository.getAll();
    }
}
