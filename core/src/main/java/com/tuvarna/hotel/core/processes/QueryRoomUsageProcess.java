package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.enums.TypeRoom;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.entities.RoomQueryDTO;
import com.tuvarna.hotel.api.models.query.room.QueryRoomUsageInput;
import com.tuvarna.hotel.api.models.query.room.QueryRoomUsageOperation;
import com.tuvarna.hotel.api.models.query.room.QueryRoomUsageOutput;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import com.tuvarna.hotel.persistence.dtos.RoomUsageDTO;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
public class QueryRoomUsageProcess extends BaseProcessor implements QueryRoomUsageOperation {
    private final ReservationRepositoryImpl reservationRepository;
    private final HotelRepositoryImpl hotelRepository;
    private static final Logger log = Logger.getLogger(QueryRoomUsageProcess.class);

    public QueryRoomUsageProcess() {
        reservationRepository = SingletonManager.getInstance(ReservationRepositoryImpl.class);
        hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
    }

    @Override
    public Either<ErrorProcessor, QueryRoomUsageOutput> process(QueryRoomUsageInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() -> {
                    log.info("Started query for rooms, input: " + input);
                    HotelEntity hotel = checkHotel(input.getHotelId());
                    checkDates(input.getStartDate(), input.getEndDate());
                    List<RoomUsageDTO> roomUsageDTOS= reservationRepository.getRoomUsageByHotelAndDate(
                            hotel.getId(),
                            input.getStartDate(),
                            input.getEndDate()
                    );
                    List<RoomQueryDTO> rooms= roomUsageDTOS.stream()
                            .map(roomUsageDTO -> RoomQueryDTO.builder()
                                    .roomNumber(roomUsageDTO.getRoomNumber())
                                    .roomType(TypeRoom.getByCode(roomUsageDTO.getRoomType().name()))
                                    .price(roomUsageDTO.getPrice())
                                    .usageCount(roomUsageDTO.getUsageCount())
                                    .build())
                            .toList();
                    QueryRoomUsageOutput result= QueryRoomUsageOutput.builder()
                            .roomsList(rooms)
                            .build();
                    log.info("Ended query for rooms, output: "+result);
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
