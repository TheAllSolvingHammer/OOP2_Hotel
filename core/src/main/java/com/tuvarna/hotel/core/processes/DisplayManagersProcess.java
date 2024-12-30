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
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@Singleton
public class DisplayManagersProcess extends BaseProcessor implements DisplayManagerOperation {
    private final UserRepositoryImpl userRepository=SingletonManager.getInstance(UserRepositoryImpl.class);
    private final ConvertUsersToManager convertUsersToManager = SingletonManager.getInstance(ConvertUsersToManager.class);

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
