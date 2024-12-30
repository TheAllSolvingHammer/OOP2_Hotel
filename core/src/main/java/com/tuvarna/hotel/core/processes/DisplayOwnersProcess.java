package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.owner.DisplayOwnersInput;
import com.tuvarna.hotel.api.models.display.owner.DisplayOwnersOperation;
import com.tuvarna.hotel.api.models.display.owner.DisplayOwnersOutput;
import com.tuvarna.hotel.api.models.entities.Owner;
import com.tuvarna.hotel.core.converters.ConvertUsersToOwner;
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
public class DisplayOwnersProcess extends BaseProcessor implements DisplayOwnersOperation {
    private final UserRepositoryImpl userRepository=SingletonManager.getInstance(UserRepositoryImpl.class);
    private final ConvertUsersToOwner convertUsersToOwner=SingletonManager.getInstance(ConvertUsersToOwner.class);

    @Override
    public Either<ErrorProcessor, DisplayOwnersOutput> process(DisplayOwnersInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    List<UserEntity> entityList = userRepository.findAllOwners();
                    List<Owner> ownerList=convertUsersToOwner.convert(entityList);
                    return DisplayOwnersOutput.builder()
                            .ownerList(ownerList)
                            .build();
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }
}
