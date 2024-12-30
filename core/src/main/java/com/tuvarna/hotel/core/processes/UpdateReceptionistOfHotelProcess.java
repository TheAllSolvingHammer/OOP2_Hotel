package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.entities.Manager;
import com.tuvarna.hotel.api.models.entities.Receptionist;
import com.tuvarna.hotel.api.models.update.receptionist.UpdateReceptionistOfHotelInput;
import com.tuvarna.hotel.api.models.update.receptionist.UpdateReceptionistOfHotelOperation;
import com.tuvarna.hotel.api.models.update.receptionist.UpdateReceptionistOfHotelOutput;
import com.tuvarna.hotel.core.exception.QueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.enums.RoleEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class UpdateReceptionistOfHotelProcess extends BaseProcessor implements UpdateReceptionistOfHotelOperation {
    private final HotelRepositoryImpl hotelRepository= SingletonManager.getInstance(HotelRepositoryImpl.class);
    private final UserRepositoryImpl userRepository=SingletonManager.getInstance(UserRepositoryImpl.class);
    @Override
    public Either<ErrorProcessor, UpdateReceptionistOfHotelOutput> process(UpdateReceptionistOfHotelInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    HotelEntity hotel=findHotel(input.getHotelID());
                    List<UserEntity> receptionistList = getUsers(input.getReceptionistList());
                    List<UserEntity> allUsers=hotel.getUserList();
                    allUsers.removeIf(u -> u.getRole() == RoleEntity.EMPLOYEE);
                    allUsers.addAll(receptionistList);
                    hotel.setUserList(allUsers);
                    hotelRepository.save(hotel);
                    return UpdateReceptionistOfHotelOutput.builder()
                            .message("Successfully updated hotel")
                            .build();
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));

    }
    private HotelEntity findHotel(UUID id){
        return hotelRepository.findHotelById(id).orElseThrow(()-> new QueryException("Hotel not found"));
    }
    private List<UserEntity> getUsers(List<Receptionist> managerList) {
        return managerList.stream()
                .map(r -> userRepository.findByID(r.getId())
                        .orElseThrow(() -> new QueryException("Error in converting")))
                .collect(Collectors.toList());
    }
}
