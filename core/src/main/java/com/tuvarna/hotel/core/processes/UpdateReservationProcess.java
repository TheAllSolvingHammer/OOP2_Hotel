package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.enums.StatusReservation;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.update.reservation.UpdateReservationInput;
import com.tuvarna.hotel.api.models.update.reservation.UpdateReservationOperation;
import com.tuvarna.hotel.api.models.update.reservation.UpdateReservationOutput;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.ReservationEntity;
import com.tuvarna.hotel.persistence.enums.ReservationStatus;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.UUID;

@Singleton
public class UpdateReservationProcess extends BaseProcessor implements UpdateReservationOperation {
    private static final Logger log = Logger.getLogger(QueryReservationsProcess.class);
    private final ReservationRepositoryImpl reservationRepository;

    public UpdateReservationProcess() {
        reservationRepository = SingletonManager.getInstance(ReservationRepositoryImpl.class);
    }


    @Override
    public Either<ErrorProcessor, UpdateReservationOutput> process(UpdateReservationInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() -> {
                    log.info("Started updating reservation, input: " + input);
                    ReservationEntity entity = checkReservation(input.getReservationID());
                    checkStatus(input.getStatus());
                    entity.setStatus(ReservationStatus.getByCode(input.getStatus().name()));
                    reservationRepository.save(entity);
                    UpdateReservationOutput result = UpdateReservationOutput.builder()
                            .message("Successfully updated reservations")
                            .build();
                    log.info("Ended updating reservation, output: " + result);
                    return result;
                }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));

    }

    private void checkStatus(StatusReservation status) {
        if (ReservationStatus.getByCode(status.name()).equals(ReservationStatus.UNKNOWN)) {
            throw new InputException("Status error");
        }
    }

    private ReservationEntity checkReservation(UUID reservationID) {
        return reservationRepository.findById(reservationID).orElseThrow(() -> new QueryException("Reservation was not found"));
    }

}
