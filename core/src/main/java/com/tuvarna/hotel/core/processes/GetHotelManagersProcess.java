package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.display.manager.Manager;
import com.tuvarna.hotel.api.models.get.manager.GetAllHotelManagersInput;
import com.tuvarna.hotel.api.models.get.manager.GetAllHotelManagersOperation;
import com.tuvarna.hotel.api.models.get.manager.GetAllHotelManagersOutput;
import com.tuvarna.hotel.core.converters.ConvertUsersToManager;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.enums.RoleEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class GetHotelManagersProcess extends BaseProcessor implements GetAllHotelManagersOperation {
    private final UserRepositoryImpl userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
    private final ConvertUsersToManager converter = SingletonManager.getInstance(ConvertUsersToManager.class);
    private final HotelRepositoryImpl hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);

    @Override
    public Either<ErrorProcessor, GetAllHotelManagersOutput> process(GetAllHotelManagersInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    List<UserEntity> users=getOwnersOfHotel(input.getHotel().getUserList());
                    List<Manager> managerList = converter.convert(users);
                    return GetAllHotelManagersOutput.builder()
                            .managerlist(managerList)
                            .build();
                }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }
    private List<UserEntity> getOwnersOfHotel(List<UUID> users){
        return users.stream().map(uuid -> userRepository.findByID(uuid)
                .orElseThrow(() -> new QueryException("Error in obtaining users")))
                .filter(userEntity -> userEntity.getRole() == RoleEntity.MANAGER)
                .collect(Collectors.toList());
    }
}
