package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.create.client.CreateClientInput;
import com.tuvarna.hotel.api.models.create.client.CreateClientOperation;
import com.tuvarna.hotel.api.models.create.client.CreateClientOutput;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.ClientRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.ClientEntity;
import com.tuvarna.hotel.persistence.enums.ClientRating;
import io.vavr.control.Either;
import io.vavr.control.Try;

@Singleton
public class CreateClientProcess extends BaseProcessor implements CreateClientOperation {
    private final ClientRepositoryImpl clientRepository= SingletonManager.getInstance(ClientRepositoryImpl.class);

    @Override
    public Either<ErrorProcessor, CreateClientOutput> process(CreateClientInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() ->{
                    ClientEntity client =ClientEntity.builder()
                            .firstName(input.getFirstName())
                            .lastName(input.getLastName())
                            .phone(input.getPhone())
                            .ucn(input.getUcn())
                            .address(input.getAddress())
                            .email(input.getEmail())
                            .birthDate(input.getBirthDate())
                            .issueDate(input.getIssueDate())
                            .expireDate(input.getExpireDate())
                            .issuedBy(input.getIssuedBy())
                            .rating(ClientRating.NORMAL)
                            .build();

                    clientRepository.save(client);
                    return CreateClientOutput.builder()
                            .message("Successfully created client")
                            .build();

        }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }
}
