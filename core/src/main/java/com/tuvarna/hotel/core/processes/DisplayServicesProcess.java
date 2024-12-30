package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.display.service.DisplayServicesInput;
import com.tuvarna.hotel.api.models.display.service.DisplayServicesOperation;
import com.tuvarna.hotel.api.models.display.service.DisplayServicesOutput;
import com.tuvarna.hotel.api.models.entities.Service;
import com.tuvarna.hotel.core.converters.ConvertServices;
import com.tuvarna.hotel.core.exception.InputQueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.ServiceRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;

import java.util.List;
@Singleton
public class DisplayServicesProcess extends BaseProcessor implements DisplayServicesOperation {
    private final ServiceRepositoryImpl serviceRepository = SingletonManager.getInstance(ServiceRepositoryImpl.class);
    private final ConvertServices converter=SingletonManager.getInstance(ConvertServices.class);
    @Override
    public Either<ErrorProcessor, DisplayServicesOutput> process(DisplayServicesInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()->{
                    List<ServiceEntity> serviceEntities = serviceRepository.getAll();
                    List<Service> serviceList=converter.convert(serviceEntities);
                    return DisplayServicesOutput.builder()
                            .serviceList(serviceList)
                            .build();

                }).toEither()
                .mapLeft(InputQueryExceptionCase::handleThrowable));

    }
}
