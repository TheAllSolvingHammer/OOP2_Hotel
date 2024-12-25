package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.create.hotel.CreateHotelInput;
import com.tuvarna.hotel.api.models.create.hotel.CreateHotelOperation;
import com.tuvarna.hotel.api.models.create.hotel.CreateHotelOutput;
import com.tuvarna.hotel.core.exception.QueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;

@Singleton
public class CreateHotelProcess extends BaseProcessor implements CreateHotelOperation {
    private final HotelRepositoryImpl hotelRepository= SingletonManager.getInstance(HotelRepositoryImpl.class);


    @Override
    public Either<ErrorProcessor, CreateHotelOutput> process(CreateHotelInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    HotelEntity hotel = HotelEntity.builder()
                            .name(input.getName())
                            .location(input.getLocation())
                            .rating(input.getRating())
                            .build();
                    hotelRepository.save(hotel);
                    return CreateHotelOutput.builder()
                            .message("Successfully added hotel")
                            .build();
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }
}

