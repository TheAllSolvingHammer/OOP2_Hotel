package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.base.OperationProcessor;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.receptionist.DisplayAllEmployeeInput;
import com.tuvarna.hotel.api.models.display.receptionist.DisplayAllEmployeeOperation;
import com.tuvarna.hotel.api.models.display.receptionist.DisplayAllEmployeeOutput;
import com.tuvarna.hotel.api.models.entities.Manager;
import com.tuvarna.hotel.api.models.entities.Receptionist;
import com.tuvarna.hotel.core.converters.ConvertUsersToManager;
import com.tuvarna.hotel.core.converters.ConvertUsersToReceptionist;
import com.tuvarna.hotel.core.exception.QueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.List;

@Singleton
public class DisplayAllEmployeeProcessor extends BaseProcessor implements DisplayAllEmployeeOperation {
    private final UserRepositoryImpl userRepository= SingletonManager.getInstance(UserRepositoryImpl.class);
    private final ConvertUsersToReceptionist converter = SingletonManager.getInstance(ConvertUsersToReceptionist.class);

    @Override
    public Either<ErrorProcessor, DisplayAllEmployeeOutput> process(DisplayAllEmployeeInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() -> {
                    List<UserEntity> entityList = userRepository.findAllReceptionist();
                    List<Receptionist> receptionistList=converter.convert(entityList);
                    return DisplayAllEmployeeOutput.builder()
                            .receptionistList(receptionistList)
                            .build();
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }
}