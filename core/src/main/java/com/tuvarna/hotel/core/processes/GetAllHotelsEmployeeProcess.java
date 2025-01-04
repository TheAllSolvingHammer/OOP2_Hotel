package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.get.receptionist.hotels.GetAllHotelsEmployeeInput;
import com.tuvarna.hotel.api.models.get.receptionist.hotels.GetAllHotelsEmployeeOperation;
import com.tuvarna.hotel.api.models.get.receptionist.hotels.GetAllHotelsEmployeeOutput;
import com.tuvarna.hotel.core.converters.ConvertEntityToHotel;
import com.tuvarna.hotel.core.exception.QueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.UUID;

@Singleton
public class GetAllHotelsEmployeeProcess extends BaseProcessor implements GetAllHotelsEmployeeOperation {
    private final UserRepositoryImpl userRepository;
    private final HotelRepositoryImpl hotelRepository;
    private final ConvertEntityToHotel converter;
    private static final Logger log = Logger.getLogger(GetAllHotelsEmployeeProcess.class);

    public GetAllHotelsEmployeeProcess() {
        userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
        hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
        converter = SingletonManager.getInstance(ConvertEntityToHotel.class);
    }

    @Override
    public Either<ErrorProcessor, GetAllHotelsEmployeeOutput> process(GetAllHotelsEmployeeInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started getting all employee's assigned hotels, input: "+input);
                    UserEntity user =checkUser(input.getId());
                    List<HotelEntity> hotelEntities = hotelRepository.findAllByEmployee(user);
                    List<Hotel> hotels = converter.convert(hotelEntities);
                    GetAllHotelsEmployeeOutput result = GetAllHotelsEmployeeOutput.builder()
                            .hotelList(hotels)
                            .build();
                    log.info("Ended getting all employee's assigned hotels, output: "+result);
                    return result;
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }

    private UserEntity checkUser(UUID id) {
        return userRepository.findByID(id).orElseThrow(()-> new QueryException("User was not found"));
    }


}
