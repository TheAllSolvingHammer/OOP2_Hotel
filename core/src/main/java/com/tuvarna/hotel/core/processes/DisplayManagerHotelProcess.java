package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.display.manager.hotel.DisplayManagerHotelInput;
import com.tuvarna.hotel.api.models.display.manager.hotel.DisplayManagerHotelOperation;
import com.tuvarna.hotel.api.models.display.manager.hotel.DisplayManagerHotelOutput;
import com.tuvarna.hotel.core.converters.ConvertEntityToHotel;
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

import java.util.List;
@Singleton
public class DisplayManagerHotelProcess extends BaseProcessor implements DisplayManagerHotelOperation {
    private final HotelRepositoryImpl hotelRepository;
    private final UserRepositoryImpl userRepository;
    private final ConvertEntityToHotel converter;
    private static final Logger log = Logger.getLogger(DisplayManagerHotelProcess.class);

    public DisplayManagerHotelProcess() {
        hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
        userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
        converter = SingletonManager.getInstance(ConvertEntityToHotel.class);
    }

    @Override
    public Either<ErrorProcessor, DisplayManagerHotelOutput> process(DisplayManagerHotelInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()-> {
                    log.info("Started displaying hotels of managers, input: "+input);
                    UserEntity user=getUserEntity(input);
                    checkManagerRole(user);
                    List<HotelEntity> hotels = hotelRepository.findAllByManager(user);
                    List<Hotel> hotelList=converter.convert(hotels);
                    DisplayManagerHotelOutput result = DisplayManagerHotelOutput.builder()
                            .hotelList(hotelList)
                            .build();
                    log.info("Ended displaying hotels of managers ,output: "+result);
                    return result;
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));

    }

    private UserEntity getUserEntity(DisplayManagerHotelInput input) {
        return userRepository.findByID(input.getId()).orElseThrow(()->new QueryException("User was not found"));
    }

    private void checkManagerRole(UserEntity user) {
        if(!user.getRole().equals(RoleEntity.MANAGER)){
            throw new QueryException("User is not manager of hotel");
        }
    }
}
