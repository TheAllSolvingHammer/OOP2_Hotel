package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.entities.Receptionist;
import com.tuvarna.hotel.api.models.get.receptionist.assigned.GetAssignedEmployeeInput;
import com.tuvarna.hotel.api.models.get.receptionist.assigned.GetAssignedEmployeeOperation;
import com.tuvarna.hotel.api.models.get.receptionist.assigned.GetAssignedEmployeeOutput;
import com.tuvarna.hotel.core.converters.ConvertUsersToReceptionist;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.enums.RoleEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class GetAssignedEmployeesProcess extends BaseProcessor implements GetAssignedEmployeeOperation {
    private final UserRepositoryImpl userRepository;
    private final ConvertUsersToReceptionist converter;
    private static final Logger log = Logger.getLogger(GetAssignedEmployeesProcess.class);

    public GetAssignedEmployeesProcess() {
        userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
        converter = SingletonManager.getInstance(ConvertUsersToReceptionist.class);
    }

    @Override
    public Either<ErrorProcessor, GetAssignedEmployeeOutput> process(GetAssignedEmployeeInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started getting all assigned employees, input: "+input);
                    List<UserEntity> users= getEmployeesOfHotel(input.getHotel().getUserList());
                    List<Receptionist> receptionistList = converter.convert(users);
                    GetAssignedEmployeeOutput result = GetAssignedEmployeeOutput.builder()
                            .receptionistList(receptionistList)
                            .build();
                    log.info("Ended getting all assigned employees, output: "+result);
                    return result;
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
