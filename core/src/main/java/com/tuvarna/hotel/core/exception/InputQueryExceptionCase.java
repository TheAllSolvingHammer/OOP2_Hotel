package com.tuvarna.hotel.core.exception;


import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import io.vavr.API;
import lombok.extern.slf4j.Slf4j;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

@Slf4j
public class InputQueryExceptionCase {
    public static ErrorProcessor handleThrowable(Throwable throwable) {
        return API.Match(throwable).of(
                Case($(instanceOf(QueryException.class)), e -> {
                    log.error("Invalid query: {}", e.getMessage());
                    return ErrorProcessor.builder()
                            .message(e.getMessage())
                            .build();
                }),
                Case($(instanceOf(InputException.class)), e -> {
                    log.error("Invalid input: {}", e.getMessage());
                    return ErrorProcessor.builder()
                            .message(e.getMessage())
                            .build();
                })

                );
    }
}
