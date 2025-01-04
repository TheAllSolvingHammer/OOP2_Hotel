package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.receptionist.DisplayAllEmployeeInput;
import com.tuvarna.hotel.api.models.display.receptionist.DisplayAllEmployeeOperation;
import com.tuvarna.hotel.api.models.display.receptionist.DisplayAllEmployeeOutput;
import com.tuvarna.hotel.api.models.entities.Receptionist;
import com.tuvarna.hotel.core.converters.ConvertUsersToReceptionist;
import com.tuvarna.hotel.core.exception.QueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;

@Singleton
public class DisplayAllEmployeeProcessor extends BaseProcessor implements DisplayAllEmployeeOperation {
    private final UserRepositoryImpl userRepository;
    private final ConvertUsersToReceptionist converter;
    private static final Logger log = Logger.getLogger(DisplayAllEmployeeProcessor.class);

    public DisplayAllEmployeeProcessor() {
        userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
        converter = SingletonManager.getInstance(ConvertUsersToReceptionist.class);
    }

    @Override
    public Either<ErrorProcessor, DisplayAllEmployeeOutput> process(DisplayAllEmployeeInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() -> {
                    log.info("Started displaying employees, input: "+input);
                    List<UserEntity> entityList = userRepository.findAllReceptionist();
                    List<Receptionist> receptionistList=converter.convert(entityList);
                    DisplayAllEmployeeOutput result = DisplayAllEmployeeOutput.builder()
                            .receptionistList(receptionistList)
                            .build();
                    log.info("Ended displaying employees , output: "+result);
                    return result;
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }
}
