package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.enums.RoleType;
import com.tuvarna.hotel.api.enums.TypeRoom;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.create.room.CreateRoomInput;
import com.tuvarna.hotel.api.models.create.room.CreateRoomOperation;
import com.tuvarna.hotel.api.models.create.room.CreateRoomOutput;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.core.exception.QueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.RoomRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.RoomEntity;
import com.tuvarna.hotel.persistence.enums.RoomType;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.UUID;

import static com.sun.el.lang.ELSupport.checkType;

@Singleton
public class CreateRoomProcess extends BaseProcessor implements CreateRoomOperation {
    private final RoomRepositoryImpl roomRepository = SingletonManager.getInstance(RoomRepositoryImpl.class);
    private final HotelRepositoryImpl hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
    @Override
    public Either<ErrorProcessor, CreateRoomOutput> process(CreateRoomInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
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
                    return CreateRoomOutput.builder()
                            .message("Successfully created room")
                            .build();
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
