package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.enums.TypeRoom;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.create.room.CreateRoomInput;
import com.tuvarna.hotel.api.models.create.room.CreateRoomOperation;
import com.tuvarna.hotel.api.models.create.room.CreateRoomOutput;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.RoomRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.RoomEntity;
import com.tuvarna.hotel.persistence.enums.RoomType;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.UUID;

@Singleton
public class CreateRoomProcess extends BaseProcessor implements CreateRoomOperation {
    private final RoomRepositoryImpl roomRepository;
    private final HotelRepositoryImpl hotelRepository;
    private static final Logger log = Logger.getLogger(CreateRoomProcess.class);

    public CreateRoomProcess() {
        roomRepository = SingletonManager.getInstance(RoomRepositoryImpl.class);
        hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
    }

    @Override
    public Either<ErrorProcessor, CreateRoomOutput> process(CreateRoomInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started create room, input: "+input);
                    HotelEntity hotel = getHotelByID(input.getHotelID());
                    checkType(input.getType());
                    RoomEntity roomEntity = RoomEntity.builder()
                            .roomNumber(input.getRoomNumber())
                            .floor(input.getFloor())
                            .price(input.getPrice())
                            .type(RoomType.getByCode(input.getType().name()))
                            .hotel(hotel)
                            .build();
                    roomRepository.save(roomEntity);
                    CreateRoomOutput result= CreateRoomOutput.builder()
                            .message("Successfully created room")
                            .build();
                    log.info("Ended create room, output: "+result);
                    return result;
                }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));
        }

    private void checkType(TypeRoom type) {
        if(RoomType.getByCode(type.name()).equals(RoomType.UNKNOWN)) {
            throw new InputException("Role types doesnt match or missing");
        }
    }

    private HotelEntity getHotelByID(UUID hotelID) {
        return hotelRepository.findHotelById(hotelID).orElseThrow(()->new QueryException("No hotel was found with such entity"));
    }

}
