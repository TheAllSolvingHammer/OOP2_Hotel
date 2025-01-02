package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.entities.Manager;
import com.tuvarna.hotel.api.models.update.hotel.UpdateHotelInput;
import com.tuvarna.hotel.api.models.update.hotel.UpdateHotelOperation;
import com.tuvarna.hotel.api.models.update.hotel.UpdateHotelOutput;
import com.tuvarna.hotel.core.converters.ConvertServicesToEntity;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import com.tuvarna.hotel.persistence.enums.RoleEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Singleton
public class UpdateHotelProcess extends BaseProcessor implements UpdateHotelOperation {
    private final HotelRepositoryImpl hotelRepository;
    private final ConvertServicesToEntity converter;
    private final UserRepositoryImpl userRepository;
    private static final Logger log = Logger.getLogger(UpdateHotelProcess.class);

    public UpdateHotelProcess() {
        hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
        converter = SingletonManager.getInstance(ConvertServicesToEntity.class);
        userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
    }

    @Override
    public Either<ErrorProcessor, UpdateHotelOutput> process(UpdateHotelInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started updating hotel, input: "+input);
                    checkInput(input);
                    HotelEntity hotel=findHotel(input.getHotelID());
                    List<ServiceEntity> serviceEntities = converter.convert(input.getServiceList());
                    hotel.setServiceList(serviceEntities);
                    List<UserEntity> managerEntities=getUsers(input.getManagerList());
                    List<UserEntity> allUsers=hotel.getUserList();
                    allUsers.removeIf(u -> u.getRole() == RoleEntity.MANAGER);
                    allUsers.addAll(managerEntities);
                    hotel.setUserList(allUsers);
                    hotelRepository.save(hotel);
                    UpdateHotelOutput result = UpdateHotelOutput.builder()
                            .message("Successfully updated the hotel")
                            .build();
                    log.info("Ended updating hotel, output: "+result);
                    return result;
                }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }
    private HotelEntity findHotel(UUID id){
        return hotelRepository.findHotelById(id).orElseThrow(()-> new QueryException("Hotel not found"));
    }


    private void checkInput(UpdateHotelInput input) {
        if(input.getServiceList().isEmpty()){
            throw new InputException("List is empty");
        }
    }
    private List<UserEntity> getUsers(List<Manager> managerList) {
        return managerList.stream()
                .map(m -> userRepository.findByID(m.getId())
                        .orElseThrow(() -> new QueryException("Error in converting")))
                .collect(Collectors.toList());
    }
}
