package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.display.manager.Manager;
import com.tuvarna.hotel.api.models.update.manager.UpdateManagersInput;
import com.tuvarna.hotel.api.models.update.manager.UpdateManagersOperation;
import com.tuvarna.hotel.api.models.update.manager.UpdateManagersOutput;
import com.tuvarna.hotel.api.models.update.service.UpdateServicesOutput;
import com.tuvarna.hotel.core.converters.ConvertServicesToEntity;
import com.tuvarna.hotel.core.exception.InputQueryPasswordExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class UpdateManagersProcess extends BaseProcessor implements UpdateManagersOperation {
    private final HotelRepositoryImpl hotelRepository= SingletonManager.getInstance(HotelRepositoryImpl.class);
    private final UserRepositoryImpl userRepository=SingletonManager.getInstance(UserRepositoryImpl.class);
    @Override
    public Either<ErrorProcessor, UpdateManagersOutput> process(UpdateManagersInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    checkInput(input);
                    HotelEntity hotel=findHotel(input.getHotelID());
                    List<UserEntity> managerEntities=getUsers(input.getManagerList());
                    List<UserEntity> allUsers=hotel.getUserList();
                    managerEntities.stream()
                            .filter(entity -> !allUsers.contains(entity)).forEach(allUsers::add);
                    hotel.setUserList(allUsers);
                    hotelRepository.save(hotel);
                    return UpdateManagersOutput.builder()
                            .message("Successfully updated the managers")
                            .build();
                }).toEither()
                .mapLeft(InputQueryPasswordExceptionCase::handleThrowable));
    }

    private void checkInput(UpdateManagersInput input) {
        if(input.getManagerList().isEmpty()){
            throw new InputException("Manager list is empty");
        }
    }

    private List<UserEntity> getUsers(List<Manager> managerList) {
        return managerList.stream()
                .map(m -> userRepository.findByID(m.getId())
                        .orElseThrow(() -> new QueryException("Error in converting")))
                .collect(Collectors.toList());
    }

    private HotelEntity findHotel(UUID id){
        return hotelRepository.findHotelById(id).orElseThrow(()-> new QueryException("Hotel not found"));
    }
}
