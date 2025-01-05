package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.enums.TypeReservation;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.entities.Reservation;
import com.tuvarna.hotel.api.models.entities.Service;
import com.tuvarna.hotel.api.models.get.reservation.ExpiringReservationInput;
import com.tuvarna.hotel.api.models.get.reservation.ExpiringReservationOperation;
import com.tuvarna.hotel.api.models.get.reservation.ExpiringReservationOutput;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.RoomRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.ReservationEntity;
import com.tuvarna.hotel.persistence.entities.RoomEntity;
import com.tuvarna.hotel.persistence.entities.UserEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.UUID;

@Singleton
public class ExpiringReservationProcess extends BaseProcessor implements ExpiringReservationOperation {
    private final ReservationRepositoryImpl reservationRepository;
    private final UserRepositoryImpl userRepository;
    private final HotelRepositoryImpl hotelRepository;
    private final RoomRepositoryImpl roomRepository;
    private static final Logger log = Logger.getLogger(ExpiringReservationProcess.class);

    public ExpiringReservationProcess() {
        reservationRepository = SingletonManager.getInstance(ReservationRepositoryImpl.class);
        userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
        hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
        roomRepository = SingletonManager.getInstance(RoomRepositoryImpl.class);
    }

    @Override
    public Either<ErrorProcessor, ExpiringReservationOutput> process(ExpiringReservationInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() -> {
                    log.info("Started process of expiring reservations, input: " + input);
                    UserEntity user = getUser(input.getUserID());
                    List<HotelEntity> hotelOfUser = hotelRepository.findAllByUser(user);
                    List<RoomEntity> roomEntityList = hotelOfUser.stream()
                            .flatMap(h -> roomRepository.findAllByHotel(h).stream())
                            .toList();
                    List<ReservationEntity> reservationEntities = reservationRepository.getAll();

                    List<Reservation> reservations = reservationEntities.stream()
                            .filter(reservationEntity -> reservationEntity.getEndDate()
                                    .equals(input.getDate()))
                            .filter(reservationEntity -> roomEntityList.contains(reservationEntity.getRoom()))
                            .map(r -> Reservation.builder()
                                    .id(r.getId())
                                    .price(r.getPrice())
                                    .type(TypeReservation.getByCode(r.getType().name()))
                                    .startDate(r.getStartDate())
                                    .endDate(r.getEndDate())
                                    .room(r.getRoom().getRoomNumber())
                                    .hotel(r.getRoom().getHotel().getName())
                                    .client(r.getClient().getFirstName() + " " + r.getClient().getLastName())
                                    .services(r.getServices().stream()
                                            .map(serviceEntity -> Service.builder()
                                                    .id(serviceEntity.getId())
                                                    .name(serviceEntity.getServiceName())
                                                    .price(serviceEntity.getPrice())
                                                    .build())
                                            .toList())
                                    .build())
                            .toList();
                    ExpiringReservationOutput result = ExpiringReservationOutput.builder()
                            .reservations(reservations)
                            .build();
                    log.info("Ended process of expiring reservations, output: " + result);
                    return result;
                }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }

    private UserEntity getUser(UUID userID) {
        return userRepository.findByID(userID).orElseThrow(() -> new QueryException("User is not in the database"));
    }

}
