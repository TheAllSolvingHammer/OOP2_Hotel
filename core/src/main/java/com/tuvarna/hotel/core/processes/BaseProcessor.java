package com.tuvarna.hotel.core.processes;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import io.vavr.control.Either;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseProcessor {
    private final Validator validator;
    private static final Logger log = Logger.getLogger(ClientInformationProcess.class);
    {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    public Either<ErrorProcessor, OperationInput> validateInput(OperationInput input) {
        Set<ConstraintViolation<OperationInput>> violations = validator.validate(input);

        if (!violations.isEmpty()) {
            List<String> errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toList());
            ErrorProcessor errorsProcessor = ErrorProcessor.builder()
                    .message(String.join(", ", errorMessages))
                    .build();
            log.info("Validation failed, cause: "+errorsProcessor);
            return Either.left(errorsProcessor);
        }

        return Either.right(input);
    }

}
