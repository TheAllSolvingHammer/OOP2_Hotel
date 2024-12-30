package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.models.create.service.CreateServiceInput;
import com.tuvarna.hotel.api.models.create.service.CreateServiceOperation;
import com.tuvarna.hotel.api.models.create.service.CreateServiceOutput;
import com.tuvarna.hotel.core.exception.QueryExceptionCase;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.domain.singleton.SingletonManager;
import com.tuvarna.hotel.persistence.daos.ServiceRepositoryImpl;
import com.tuvarna.hotel.persistence.entities.ServiceEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;

@Singleton
public class CreateServiceProcess extends BaseProcessor implements CreateServiceOperation {
    private final ServiceRepositoryImpl serviceRepository = SingletonManager.getInstance(ServiceRepositoryImpl.class);
    @Override
    public Either<ErrorProcessor, CreateServiceOutput> process(CreateServiceInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()-> {
                    ServiceEntity service = ServiceEntity.builder()
                            .serviceName(input.getServiceName())
                            .price(input.getPrice())
                            .build();
                    serviceRepository.save(service);
                    return CreateServiceOutput.builder()
                            .message("Successfully created service")
                            .build();
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));

    }
}
