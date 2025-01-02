package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.create.hotel.CreateHotelInput;
import com.tuvarna.hotel.api.models.create.hotel.CreateHotelOperation;
import com.tuvarna.hotel.api.models.create.hotel.CreateHotelOutput;
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
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
public class CreateHotelProcess extends BaseProcessor implements CreateHotelOperation {
    private final HotelRepositoryImpl hotelRepository;
    private final UserRepositoryImpl userRepository;
    private static final Logger log = Logger.getLogger(CreateHotelProcess.class);

    public CreateHotelProcess() {
        hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
        userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
    }

    @Override
    public Either<ErrorProcessor, CreateHotelOutput> process(CreateHotelInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started create hotel process, input: "+input);
                    UserEntity user = findUser(input.getOwnerID());
                    checkUserRoles(user);
                    List<UserEntity> users=new ArrayList<>();
                    users.add(user);
                    HotelEntity hotel = HotelEntity.builder()
                            .name(input.getName())
                            .location(input.getLocation())
                            .rating(input.getRating())
                            .userList(users)
                            .build();
                    hotelRepository.save(hotel);
                    CreateHotelOutput result= CreateHotelOutput.builder()
                            .message("Successfully added hotel")
                            .build();
                    log.info("Ended create hotel process, output"+result);
                    return result;
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }

    private void checkUserRoles(UserEntity user) {
        if(!user.getRole().equals(RoleEntity.OWNER)){
            throw new QueryException("User doesnt have owner privileges");
        }
    }

    private UserEntity findUser(UUID id){
        return userRepository.findByID(id).orElseThrow(()-> new QueryException("User not found"));

    }
}

