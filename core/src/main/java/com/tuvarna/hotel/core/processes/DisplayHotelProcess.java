package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.hotel.DisplayHotelsInput;
import com.tuvarna.hotel.api.models.display.hotel.DisplayHotelsOperation;
import com.tuvarna.hotel.api.models.display.hotel.DisplayHotelsOutput;
import com.tuvarna.hotel.api.models.entities.Hotel;
import com.tuvarna.hotel.core.converters.ConvertEntityToHotel;
import com.tuvarna.hotel.core.exception.QueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;

@Singleton
public class DisplayHotelProcess extends BaseProcessor implements DisplayHotelsOperation {
    private final HotelRepositoryImpl hotelRepository;
    private final ConvertEntityToHotel converter;
    private static final Logger log = Logger.getLogger(DisplayHotelProcess.class);

    public DisplayHotelProcess() {
        hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
        converter = SingletonManager.getInstance(ConvertEntityToHotel.class);
    }

    @Override
    public Either<ErrorProcessor, DisplayHotelsOutput> process(DisplayHotelsInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() -> {
                    log.info("Started displaying hotels, input: " + input);
                    List<HotelEntity> entityList = hotelRepository.getAll();
                    List<Hotel> hotels = converter.convert(entityList);
                    DisplayHotelsOutput result = DisplayHotelsOutput.builder()
                            .hotelList(hotels)
                            .build();
                    log.info("Ended displaying hotels, output: "+result);
                    return result;
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }
}
