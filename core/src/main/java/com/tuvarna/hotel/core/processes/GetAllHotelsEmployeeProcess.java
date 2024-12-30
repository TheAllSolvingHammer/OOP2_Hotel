package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.api.models.get.receptionist.GetAllHotelsEmployeeInput;
import com.tuvarna.hotel.api.models.get.receptionist.GetAllHotelsEmployeeOperation;
import com.tuvarna.hotel.api.models.get.receptionist.GetAllHotelsEmployeeOutput;
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

import java.util.List;
import java.util.UUID;

@Singleton
public class GetAllHotelsEmployeeProcess extends BaseProcessor implements GetAllHotelsEmployeeOperation {
    private final UserRepositoryImpl userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
    private final HotelRepositoryImpl hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
    private final ConvertEntityToHotel converter = SingletonManager.getInstance(ConvertEntityToHotel.class);

    @Override
    public Either<ErrorProcessor, GetAllHotelsEmployeeOutput> process(GetAllHotelsEmployeeInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    UserEntity user =checkUser(input.getId());
                    List<HotelEntity> hotelEntities = hotelRepository.findAllByEmployee(user);
                    List<Hotel> hotels = converter.convert(hotelEntities);
                    return GetAllHotelsEmployeeOutput.builder()
                            .hotelList(hotels)
                            .build();
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }

    private UserEntity checkUser(UUID id) {
        return userRepository.findByID(id).orElseThrow(()-> new QueryException("User was not found"));
    }


}
