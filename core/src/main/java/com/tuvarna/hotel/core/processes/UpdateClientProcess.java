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
import org.apache.log4j.Logger;

import java.util.UUID;

@Singleton
public class UpdateClientProcess extends BaseProcessor implements UpdateClientOperation {
    private final ClientRepositoryImpl clientRepository;
    private static final Logger log = Logger.getLogger(UpdateClientProcess.class);

    public UpdateClientProcess() {
        clientRepository = SingletonManager.getInstance(ClientRepositoryImpl.class);
    }

    @Override
    public Either<ErrorProcessor, UpdateClientOutput> process(UpdateClientInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started updating client, input: "+input);
                    ClientEntity client = findClient(input.getClient());
                    client.setRating(ClientRating.getByCode(input.getRatingClient().name()));
                    clientRepository.save(client);
                    UpdateClientOutput result = UpdateClientOutput.builder()
                            .message("Successfully updated client")
                            .build();
                    log.info("Ended updating client, output: "+result);
                    return result;
        }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }

    private ClientEntity findClient(UUID id){
        return clientRepository.findById(id).orElseThrow(()-> new QueryException("Client not found."));
    }
}
