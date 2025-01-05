package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.entities.Reservation;
import com.tuvarna.hotel.api.models.query.reservation.QueryReservationInput;
import com.tuvarna.hotel.api.models.query.reservation.QueryReservationOperation;
import com.tuvarna.hotel.api.models.query.reservation.QueryReservationOutput;
import com.tuvarna.hotel.core.converters.ConvertEntityToReservation;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.ReservationEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
public class QueryReservationsProcess extends BaseProcessor implements QueryReservationOperation {
    private static final Logger log = Logger.getLogger(QueryReservationsProcess.class);
    private final ReservationRepositoryImpl reservationRepository;
    private final HotelRepositoryImpl hotelRepository;
    private final ConvertEntityToReservation converter;
    public QueryReservationsProcess() {
        reservationRepository = SingletonManager.getInstance(ReservationRepositoryImpl.class);
        hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
        converter=SingletonManager.getInstance(ConvertEntityToReservation.class);
    }

    @Override
    public Either<ErrorProcessor, QueryReservationOutput> process(QueryReservationInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started query for reservations, input: "+input);
                    HotelEntity hotel = checkHotel(input.getHotelId());
                    checkDates(input.getStartDate(), input.getEndDate());
                    List<ReservationEntity> reservationEntities = reservationRepository.getReservationsByHotelAndDate(hotel.getId(),input.getStartDate(),input.getEndDate());
                    List<Reservation> reservations= convertReservations(reservationEntities);
                    QueryReservationOutput result = QueryReservationOutput.builder()
                            .reservations(reservations)
                            .build();
                    log.info("Ended query for reservations, output: "+result);
                    return result;
                }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }

    private List<Reservation> convertReservations(List<ReservationEntity> reservationEntities) {
        return converter.convert(reservationEntities);

    }

    private HotelEntity checkHotel(UUID hotelId) {
        return hotelRepository.findHotelById(hotelId).orElseThrow(() -> new QueryException("Hotel was not found"));
    }

    private void checkDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new InputException("Start date can not be after the end date");
        }
    }
}
