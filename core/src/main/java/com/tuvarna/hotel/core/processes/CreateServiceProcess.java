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
import org.apache.log4j.Logger;

@Singleton
public class CreateServiceProcess extends BaseProcessor implements CreateServiceOperation {
    private final ServiceRepositoryImpl serviceRepository;
    private static final Logger log = Logger.getLogger(CreateServiceProcess.class);

    public CreateServiceProcess() {
        serviceRepository = SingletonManager.getInstance(ServiceRepositoryImpl.class);
    }

    @Override
    public Either<ErrorProcessor, CreateServiceOutput> process(CreateServiceInput input) {
        return validateInput(input).flatMap(validInput -> Try.of(()-> {
                    log.info("Started create service process, input: "+input);
                    ServiceEntity service = ServiceEntity.builder()
                            .serviceName(input.getServiceName())
                            .price(input.getPrice())
                            .build();
                    serviceRepository.save(service);
                    CreateServiceOutput result= CreateServiceOutput.builder()
                            .message("Successfully created service")
                            .build();
                    log.info("Ended create service process, output: "+result);
                    return result;
                }).toEither()
                .mapLeft(QueryExceptionCase::handleThrowable));

    }
}
