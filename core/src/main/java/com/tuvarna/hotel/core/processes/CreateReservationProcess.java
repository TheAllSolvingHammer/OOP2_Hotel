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
import com.tuvarna.hotel.core.converters.ConvertServicesToEntity;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.ClientRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.RoomRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.UserRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.*;
import com.tuvarna.hotel.persistence.enums.ClientRating;
import com.tuvarna.hotel.persistence.enums.ReservationType;
import io.vavr.control.Either;
import io.vavr.control.Try;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;


@Singleton
public class CreateReservationProcess extends BaseProcessor implements CreateReservationOperation {
    private final RoomRepositoryImpl roomRepository;
    private final ReservationRepositoryImpl reservationRepository;
    private final ClientRepositoryImpl clientRepository;
    private final UserRepositoryImpl userRepository;
    private final ConvertServicesToEntity converter;
    private static final Logger log = Logger.getLogger(CreateReservationProcess.class);

    public CreateReservationProcess() {
        roomRepository = SingletonManager.getInstance(RoomRepositoryImpl.class);
        reservationRepository = SingletonManager.getInstance(ReservationRepositoryImpl.class);
        clientRepository = SingletonManager.getInstance(ClientRepositoryImpl.class);
        userRepository = SingletonManager.getInstance(UserRepositoryImpl.class);
        converter = SingletonManager.getInstance(ConvertServicesToEntity.class);
    }

    @Override
    public Either<ErrorProcessor, CreateReservationOutput> process(CreateReservationInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started create reservation input: "+input);
                    checkDates(input.getStartDate(),input.getEndDate());
                    RoomEntity room = checkRoom(input.getRoom());
                    checkAvailability(room,input.getStartDate(),input.getEndDate());
                    ClientEntity clientEntity= checkClient(input.getClient());
                    checkClientRating(clientEntity);
                    ReservationType type = getType(input.getType());
                    BigDecimal price = calculatePrice(input.getServices(),input.getStartDate(),input.getEndDate(),room);
                    List<ServiceEntity> serviceEntities=converter.convert(input.getServices());
                    UserEntity user = getUser(input.getId());
                    ReservationEntity reservation= ReservationEntity.builder()
                            .client(clientEntity)
                            .startDate(input.getStartDate())
                            .endDate(input.getEndDate())
                            .price(price)
                            .type(type)
                            .room(room)
                            .services(serviceEntities)
                            .createdBy(user)
                            .build();
                    reservationRepository.save(reservation);
                CreateReservationOutput result= CreateReservationOutput.builder()
                        .message("Successfully created reservation")
                        .build();
                log.info("Ended create reservation, output: "+result);
                return result;
                }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
    }

    private void checkClientRating(ClientEntity clientEntity) {
        if(clientEntity.getRating().equals(ClientRating.BAD)) {
            throw new InputException("Client has bad rating!");
        }
        if(clientEntity.getRating().equals(ClientRating.CONCERNING)){
            log.warn("Client is concerning, client: "+clientEntity);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Client waring");
            alert.initStyle(StageStyle.UNDECORATED);
            alert.setContentText("Client rating is rated as concerning");
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.CANCEL)
                    throw new InputException("Client was marked as concerning");
            });


        }
    }

    private UserEntity getUser(UUID id) {
        return userRepository.findByID(id).orElseThrow(()->new QueryException("No user was found"));
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
