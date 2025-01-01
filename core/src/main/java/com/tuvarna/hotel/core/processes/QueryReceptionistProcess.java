package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.entities.ReceptionistDTO;
import com.tuvarna.hotel.api.models.query.receptionist.information.QueryReceptionistInput;
import com.tuvarna.hotel.api.models.query.receptionist.information.QueryReceptionistOperation;
import com.tuvarna.hotel.api.models.query.receptionist.information.QueryReceptionistOutput;
import com.tuvarna.hotel.core.exception.InputQueryPasswordExceptionCase;
import com.tuvarna.hotel.core.exception.QueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import com.tuvarna.hotel.persistence.dtos.ReceptionistReservationDTO;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.List;
@Singleton
public class QueryReceptionistProcess extends BaseProcessor implements QueryReceptionistOperation {
    private final ReservationRepositoryImpl reservationRepository = SingletonManager.getInstance(ReservationRepositoryImpl.class);
    @Override
    public Either<ErrorProcessor, QueryReceptionistOutput> process(QueryReceptionistInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    List<ReceptionistReservationDTO> receptionistsList = reservationRepository.getReservationsByReceptionists(input.getHotelId(),input.getStartDate(),input.getEndDate());
                    List<ReceptionistDTO> converted = receptionistsList.stream()
                            .map(receptionistReservationDTO -> ReceptionistDTO.builder()
                                    .receptionistName(receptionistReservationDTO.getReceptionistName())
                                    .reservationID(receptionistReservationDTO.getReservationID())
                                    .build()).toList();
                    return QueryReceptionistOutput.builder()
                            .receptionistDTOS(converted)
                            .build();

                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }
}