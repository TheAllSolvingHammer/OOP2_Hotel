package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import com.tuvarna.hotel.api.models.update.service.UpdateServicesInput;
import com.tuvarna.hotel.api.models.update.service.UpdateServicesOperation;
import com.tuvarna.hotel.api.models.update.service.UpdateServicesOutput;
import com.tuvarna.hotel.core.converters.ConvertServicesToEntity;
import com.tuvarna.hotel.core.exception.InputQueryPasswordExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.HotelRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.HotelEntity;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.List;
import java.util.UUID;
@Singleton
public class UpdateServicesProcess extends BaseProcessor implements UpdateServicesOperation {
    private final HotelRepositoryImpl hotelRepository= SingletonManager.getInstance(HotelRepositoryImpl.class);
    private final ConvertServicesToEntity converter = SingletonManager.getInstance(ConvertServicesToEntity.class);
    @Override
    public Either<ErrorProcessor, UpdateServicesOutput> process(UpdateServicesInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    checkInput(input);

                    HotelEntity hotel=findHotel(input.getHotelID());
                    List<ServiceEntity> serviceEntities = converter.convert(input.getServiceList());
                    hotel.setServiceList(serviceEntities);
                    hotelRepository.save(hotel);
                    return UpdateServicesOutput.builder()
                            .message("Successfully updated the services")
                            .build();
                }).toEither()
                .mapLeft(InputQueryPasswordExceptionCase::handleThrowable));
    }
    private HotelEntity findHotel(UUID id){
        return hotelRepository.findHotelById(id).orElseThrow(()-> new QueryException("Hotel not found"));
    }


    private void checkInput(UpdateServicesInput input) {
        if(input.getServiceList().isEmpty()){
            throw new InputException("List is empty");
        }
    }
}
