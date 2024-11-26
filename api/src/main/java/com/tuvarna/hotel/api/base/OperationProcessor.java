package com.tuvarna.hotel.api.base;

import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import io.vavr.control.Either;

public interface OperationProcessor<T extends OperationOutput,E extends OperationInput> {
    Either<ErrorProcessor,T> process(E input);
}
