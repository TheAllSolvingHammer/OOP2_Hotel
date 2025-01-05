package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.display.reservation.DisplayAllReservationsHotelInput;
import com.tuvarna.hotel.api.models.display.reservation.DisplayAllReservationsHotelOperation;
import com.tuvarna.hotel.api.models.display.reservation.DisplayAllReservationsHotelOutput;
import com.tuvarna.hotel.api.models.entities.Reservation;
import com.tuvarna.hotel.core.converters.ConvertEntityToReservation;
import com.tuvarna.hotel.core.exception.QueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.ReservationEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.UUID;

@Singleton
public class DisplayAllReservationsHotelProcess extends BaseProcessor implements DisplayAllReservationsHotelOperation {
    private final ReservationRepositoryImpl reservationRepository;
    private final HotelRepositoryImpl hotelRepository;
    private final ConvertEntityToReservation converter;
    private static final Logger log = Logger.getLogger(DisplayAllReservationsHotelProcess.class);

    public DisplayAllReservationsHotelProcess() {
        reservationRepository = SingletonManager.getInstance(ReservationRepositoryImpl.class);
        hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
        converter=SingletonManager.getInstance(ConvertEntityToReservation.class);
    }

    @Override
    public Either<ErrorProcessor, DisplayAllReservationsHotelOutput> process(DisplayAllReservationsHotelInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started displaying all reservations, input: "+input);
                    HotelEntity hotel = checkHotel(input.getHotelID());
                    List<ReservationEntity> reservationEntities= reservationRepository.findAllByHotelId(hotel.getId());
                    List<Reservation> reservations = converter.convert(reservationEntities);
                    DisplayAllReservationsHotelOutput result= DisplayAllReservationsHotelOutput
                            .builder()
                            .reservations(reservations)
                            .build();
                    log.info("Ended displaying all reservation, output: "+result);
                    return result;
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));

    }

    private HotelEntity checkHotel(UUID hotelId) {
        return hotelRepository.findHotelById(hotelId).orElseThrow(() -> new QueryException("Hotel was not found"));
    }

}
