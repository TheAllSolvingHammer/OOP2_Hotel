package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.entities.ServicesDTO;
import com.tuvarna.hotel.api.models.query.reservation.QueryServicesInput;
import com.tuvarna.hotel.api.models.query.reservation.QueryServicesOperation;
import com.tuvarna.hotel.api.models.query.reservation.QueryServicesOutput;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.daos.ReservationRepositoryImpl;
import com.tuvarna.hotel.persistence.dtos.ServiceUsageDTO;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
public class QueryServicesProcess extends BaseProcessor implements QueryServicesOperation {
    private final HotelRepositoryImpl hotelRepository;
    private final ReservationRepositoryImpl reservationRepository;
    private static final Logger log = Logger.getLogger(QueryServicesProcess.class);

    public QueryServicesProcess() {
        hotelRepository = SingletonManager.getInstance(HotelRepositoryImpl.class);
        reservationRepository = SingletonManager.getInstance(ReservationRepositoryImpl.class);
    }

    @Override
    public Either<ErrorProcessor, QueryServicesOutput> process(QueryServicesInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(() -> {
                    log.info("Started query for services, input: " + input);
                    HotelEntity hotel = checkHotel(input.getHotelId());
                    checkDates(input.getStartDate(), input.getEndDate());
                    List<ServiceUsageDTO> serviceUsageDTOS = reservationRepository.countServiceUsage(hotel.getId(), input.getStartDate(), input.getEndDate());
                    List<ServicesDTO> servicesDTOS = serviceUsageDTOS.stream().map(s -> ServicesDTO.builder()
                            .serviceName(s.getServiceName())
                            .usageCount(s.getUsageCount())
                            .totalRevenue(s.getTotalRevenue())
                            .build()).toList();
                    QueryServicesOutput result = QueryServicesOutput.builder()
                            .servicesDTOList(servicesDTOS)
                            .build();
                    log.info("Ended query for services, input: "+result);
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
