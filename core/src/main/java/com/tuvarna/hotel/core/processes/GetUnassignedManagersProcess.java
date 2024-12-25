package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.manager.Manager;
import com.tuvarna.hotel.api.models.get.manager.GetAllUnassignedManagersInput;
import com.tuvarna.hotel.api.models.get.manager.GetAllUnassignedManagersOperation;
import com.tuvarna.hotel.api.models.get.manager.GetAllUnassignedManagersOutput;
import com.tuvarna.hotel.core.converters.ConvertUsersToManager;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.List;
@Singleton
public class GetUnassignedManagersProcess extends BaseProcessor implements GetAllUnassignedManagersOperation {
    private final UserRepositoryImpl userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
    private final ConvertUsersToManager converter = SingletonManager.getInstance(ConvertUsersToManager.class);
    @Override
    public Either<ErrorProcessor, GetAllUnassignedManagersOutput> process(GetAllUnassignedManagersInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    List<UserEntity> users=userRepository.findAllUnassignedManagers();
                    List<Manager> managerList = converter.convert(users);
                    return GetAllUnassignedManagersOutput.builder()
                            .managerlist(managerList)
                            .build();
                }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }
}
