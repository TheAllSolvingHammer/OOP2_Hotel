package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.update.client.UpdateClientInput;
import com.tuvarna.hotel.api.models.update.client.UpdateClientOperation;
import com.tuvarna.hotel.api.models.update.client.UpdateClientOutput;
import com.tuvarna.hotel.core.exception.QueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.ClientRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.ClientEntity;
import com.tuvarna.hotel.persistence.enums.ClientRating;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.UUID;

@Singleton
public class UpdateClientProcess extends BaseProcessor implements UpdateClientOperation {
    private final ClientRepositoryImpl clientRepository = SingletonManager.getInstance(ClientRepositoryImpl.class);
    @Override
    public Either<ErrorProcessor, UpdateClientOutput> process(UpdateClientInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    ClientEntity client = findClient(input.getClient());
                    client.setRating(ClientRating.getByCode(input.getRatingClient().name()));
                    clientRepository.save(client);

                    return UpdateClientOutput.builder()
                            .message("Successfully updated client")
                            .build();

        }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }

    private ClientEntity findClient(UUID id){
        return clientRepository.findById(id).orElseThrow(()-> new QueryException("Client not found."));
    }
}
