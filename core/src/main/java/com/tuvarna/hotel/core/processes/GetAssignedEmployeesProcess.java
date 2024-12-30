package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.entities.Receptionist;
import com.tuvarna.hotel.api.models.get.receptionist.assigned.GetAssignedEmployeeInput;
import com.tuvarna.hotel.api.models.get.receptionist.assigned.GetAssignedEmployeeOperation;
import com.tuvarna.hotel.api.models.get.receptionist.assigned.GetAssignedEmployeeOutput;
import com.tuvarna.hotel.api.models.get.rooms.GetAllRoomsPerHotelOutput;
import com.tuvarna.hotel.core.converters.ConvertUsersToReceptionist;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.enums.RoleEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class GetAssignedEmployeesProcess extends BaseProcessor implements GetAssignedEmployeeOperation {
    private final UserRepositoryImpl userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
    private final ConvertUsersToReceptionist converter =SingletonManager.getInstance(ConvertUsersToReceptionist.class);
    @Override
    public Either<ErrorProcessor, GetAssignedEmployeeOutput> process(GetAssignedEmployeeInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    System.out.println("My users before convertion are"+input.getHotel().getUserList());
                    List<UserEntity> users= getEmployeesOfHotel(input.getHotel().getUserList());
                    System.out.println("My users are: "+users);
                    List<Receptionist> receptionistList = converter.convert(users);
                    return GetAssignedEmployeeOutput.builder()
                            .receptionistList(receptionistList)
                            .build();
                }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
        }

    private List<UserEntity> getEmployeesOfHotel(List<UUID> users) {
        return users.stream().map(uuid -> userRepository.findByID(uuid)
                        .orElseThrow(() -> new QueryException("Error in obtaining users")))
                .filter(userEntity -> userEntity.getRole() == RoleEntity.EMPLOYEE)
                .collect(Collectors.toList());
    }

}
