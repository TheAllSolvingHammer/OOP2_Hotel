package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.entities.Reservations;
import com.tuvarna.hotel.api.models.query.receptionist.information.QueryReceptionistInput;
import com.tuvarna.hotel.api.models.query.receptionist.information.QueryReceptionistOperation;
import com.tuvarna.hotel.api.models.query.receptionist.information.QueryReceptionistOutput;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import com.tuvarna.hotel.persistence.dtos.ReceptionistReservationDTO;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
public class QueryReceptionistProcess extends BaseProcessor implements QueryReceptionistOperation {
    private final ReservationRepositoryImpl reservationRepository;
    private final HotelRepositoryImpl hotelRepository;
    private static final Logger log = Logger.getLogger(QueryReceptionistProcess.class);

    public QueryReceptionistProcess() {
        reservationRepository = SingletonManager.getInstance(ReservationRepositoryImpl.class);
        hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
    }

    @Override
    public Either<ErrorProcessor, QueryReceptionistOutput> process(QueryReceptionistInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started query for receptionists, input: "+input);
                    HotelEntity hotel = checkHotel(input.getHotelId());
                    checkDates(input.getStartDate(), input.getEndDate());
                    List<ReceptionistReservationDTO> receptionistsList = reservationRepository.getReservationsByReceptionists(hotel.getId(),input.getStartDate(),input.getEndDate());
                    List<Reservations> converted = receptionistsList.stream()
                            .map(receptionistReservationDTO -> Reservations.builder()
                                    .receptionistName(receptionistReservationDTO.getReceptionistName())
                                    .reservationID(receptionistReservationDTO.getReservationID())
                                    .build()).toList();
                    QueryReceptionistOutput result = QueryReceptionistOutput.builder()
                            .reservations(converted)
                            .build();
                    log.info("Ended query for receptionist, output: "+result);
                    return result;

                }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
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
