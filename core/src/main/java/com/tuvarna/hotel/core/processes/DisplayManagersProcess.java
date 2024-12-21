package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.manager.DisplayManagerInput;
import com.tuvarna.hotel.api.models.display.manager.DisplayManagerOperation;
import com.tuvarna.hotel.api.models.display.manager.DisplayManagerOutput;
import com.tuvarna.hotel.api.models.display.manager.Manager;
import com.tuvarna.hotel.api.models.display.owner.DisplayOwnersOutput;
import com.tuvarna.hotel.api.models.display.owner.Owner;
import com.tuvarna.hotel.core.converters.ConvertUsersToManager;
import com.tuvarna.hotel.core.exception.QueryExceptionCase;
import com.tuvarna.hotel.domain.aspect.LogExecution;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.List;

@Singleton
public class DisplayManagersProcess extends BaseProcessor implements DisplayManagerOperation {

    private final UserRepositoryImpl userRepository;
    private final ConvertUsersToManager convertUsersToManager;

    public DisplayManagersProcess() {
        this.userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
        this.convertUsersToManager = SingletonManager.getInstance(ConvertUsersToManager.class);
    }

    @Override
    public Either<ErrorProcessor, DisplayManagerOutput> process(DisplayManagerInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    List<UserEntity> entityList = userRepository.findAllManagers();
                    List<Manager> managerList=convertUsersToManager.convert(entityList);
                    return DisplayManagerOutput.builder()
                            .managerList(managerList)
                            .build();
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }
}
