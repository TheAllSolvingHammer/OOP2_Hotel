package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.manager.list.DisplayManagerInput;
import com.tuvarna.hotel.api.models.display.manager.list.DisplayManagerOperation;
import com.tuvarna.hotel.api.models.display.manager.list.DisplayManagerOutput;
import com.tuvarna.hotel.api.models.entities.Manager;
import com.tuvarna.hotel.core.converters.ConvertUsersToManager;
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
public class DisplayManagersProcess extends BaseProcessor implements DisplayManagerOperation {
    private final UserRepositoryImpl userRepository;
    private final ConvertUsersToManager convertUsersToManager;
    private static final Logger log = Logger.getLogger(DisplayManagersProcess.class);

    public DisplayManagersProcess() {
        userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
        convertUsersToManager = SingletonManager.getInstance(ConvertUsersToManager.class);
    }

    @Override
    public Either<ErrorProcessor, DisplayManagerOutput> process(DisplayManagerInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started displaying managers, input: "+input);
                    List<UserEntity> entityList = userRepository.findAllManagers();
                    List<Manager> managerList=convertUsersToManager.convert(entityList);
                    DisplayManagerOutput result = DisplayManagerOutput.builder()
                            .managerList(managerList)
                            .build();
                    log.info("Ended displaying managers, output: "+result);
                    return result;
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }
}
