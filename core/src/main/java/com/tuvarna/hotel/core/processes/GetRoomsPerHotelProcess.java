package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.entities.Room;
import com.tuvarna.hotel.api.models.get.rooms.GetAllRoomsPerHotelInput;
import com.tuvarna.hotel.api.models.get.rooms.GetAllRoomsPerHotelOperation;
import com.tuvarna.hotel.api.models.get.rooms.GetAllRoomsPerHotelOutput;
import com.tuvarna.hotel.core.converters.ConvertEntityToRoom;
import com.tuvarna.hotel.core.exception.QueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.RoomRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.RoomEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.UUID;

@Singleton
public class GetRoomsPerHotelProcess extends BaseProcessor implements GetAllRoomsPerHotelOperation {
    private final RoomRepositoryImpl roomRepository;
    private final HotelRepositoryImpl hotelRepository;
    private final ConvertEntityToRoom converter;
    private static final Logger log = Logger.getLogger(GetRoomsPerHotelProcess.class);

    public GetRoomsPerHotelProcess() {
        roomRepository = SingletonManager.getInstance(RoomRepositoryImpl.class);
        hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
        converter = SingletonManager.getInstance(ConvertEntityToRoom.class);
    }

    @Override
    public Either<ErrorProcessor, GetAllRoomsPerHotelOutput> process(GetAllRoomsPerHotelInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    log.info("Started getting rooms per hotel, input: "+input);
                    HotelEntity hotel = getHotelByID(input.getHotelID());
                    List<RoomEntity> roomEntities = roomRepository.findAllByHotel(hotel);
                    List<Room> rooms = converter.convert(roomEntities);
                    GetAllRoomsPerHotelOutput result = GetAllRoomsPerHotelOutput.builder()
                            .roomList(rooms)
                            .build();
                    log.info("Ended getting rooms per hotel, output: "+result);
                    return result;

         }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));
    }

    private HotelEntity getHotelByID(UUID hotelID) {
        return hotelRepository.findHotelById(hotelID).orElseThrow(()->new QueryException("No hotel was found"));
    }
}
