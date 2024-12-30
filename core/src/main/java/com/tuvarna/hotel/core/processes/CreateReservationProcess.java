package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.enums.TypeReservation;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.create.reservation.CreateReservationInput;
import com.tuvarna.hotel.api.models.create.reservation.CreateReservationOperation;
import com.tuvarna.hotel.api.models.create.reservation.CreateReservationOutput;
import com.tuvarna.hotel.api.models.entities.Client;
import com.tuvarna.hotel.api.models.entities.Room;
import com.tuvarna.hotel.api.models.entities.Service;
import com.tuvarna.hotel.core.converters.ConvertServices;
import com.tuvarna.hotel.core.converters.ConvertServicesToEntity;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.ClientRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.RoomRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.ClientEntity;
import com.tuvarna.hotel.persistence.entities.ReservationEntity;
import com.tuvarna.hotel.persistence.entities.RoomEntity;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import com.tuvarna.hotel.persistence.enums.ReservationType;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;


@Singleton
public class CreateReservationProcess extends BaseProcessor implements CreateReservationOperation {
    private final RoomRepositoryImpl roomRepository = SingletonManager.getInstance(RoomRepositoryImpl.class);
    private final ReservationRepositoryImpl reservationRepository = SingletonManager.getInstance(ReservationRepositoryImpl.class);
    private final ClientRepositoryImpl clientRepository= SingletonManager.getInstance(ClientRepositoryImpl.class);
    private final ConvertServicesToEntity converter = SingletonManager.getInstance(ConvertServicesToEntity.class);
    @Override
    public Either<ErrorProcessor, CreateReservationOutput> process(CreateReservationInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    checkDates(input.getStartDate(),input.getEndDate());
                    RoomEntity room = checkRoom(input.getRoom());
                    checkAvailability(room,input.getStartDate(),input.getEndDate());
                    ClientEntity clientEntity= checkClient(input.getClient());
                    ReservationType type = getType(input.getType());
                    BigDecimal price = calculatePrice(input.getServices(),input.getStartDate(),input.getEndDate(),room);
                    List<ServiceEntity> serviceEntities=converter.convert(input.getServices());
                    ReservationEntity reservation= ReservationEntity.builder()
                            .client(clientEntity)
                            .startDate(input.getStartDate())
                            .endDate(input.getEndDate())
                            .price(price)
                            .type(type)
                            .room(room)
                            .services(serviceEntities)
                            .build();
                    reservationRepository.save(reservation);
                return CreateReservationOutput.builder()
                        .message("Successfully created reservation")
                        .build();
                }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }

    private ReservationType getType(TypeReservation type) {
        if(ReservationType.getByCode(type.name()).equals(ReservationType.UNKNOWN)){
            throw new InputException("Error in getting the type of the reservation");
        }
        return ReservationType.getByCode(type.name());
    }

    private BigDecimal calculatePrice(List<Service> services, LocalDate startDate, LocalDate endDate, RoomEntity room) {
        BigDecimal val=new BigDecimal(0);
        for(Service s:services) val = val.add(s.getPrice());
        return val.add(room.getPrice().multiply(BigDecimal.valueOf(DAYS.between(startDate, endDate))));
    }

    private ClientEntity checkClient(Client client) {
        return clientRepository.findById(client.getId()).orElseThrow(()-> new QueryException("No client was found"));
    }

    private void checkDates(LocalDate startDate, LocalDate endDate) {
        if(startDate.isAfter(endDate)){
            throw new InputException("Start date can not be after end date");
        }
    }

    private void checkAvailability(RoomEntity room, LocalDate startDate, LocalDate endDate) {
        if(!reservationRepository.isRoomAvailable(room,startDate,endDate)){
            throw new QueryException("The room is not available");
        }
    }

    private RoomEntity checkRoom(Room room) {
        return roomRepository.findById(room.getRoomID()).orElseThrow(()->new QueryException("Room was not correct"));

    }

}
