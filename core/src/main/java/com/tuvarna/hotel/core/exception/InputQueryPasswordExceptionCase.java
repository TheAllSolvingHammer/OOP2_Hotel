package com.tuvarna.hotel.core.exception;


import com.tuvarna.hotel.api.exceptions.ErrorProcessor;
import com.tuvarna.hotel.api.exceptions.InputException;
import com.tuvarna.hotel.api.exceptions.PasswordException;
import com.tuvarna.hotel.api.exceptions.QueryException;
import io.vavr.API;
import org.apache.log4j.Logger;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;


public class InputQueryPasswordExceptionCase {
    private static final Logger log = Logger.getLogger(InputQueryPasswordExceptionCase.class);
    public static ErrorProcessor handleThrowable(Throwable throwable) {
        return API.Match(throwable).of(
                Case($(instanceOf(QueryException.class)), e -> {
                    ErrorProcessor errorProcessor = ErrorProcessor.builder()
                            .message(e.getMessage())
                            .build();
                    log.error("Error encountered at Query, input: "+errorProcessor);
                    return errorProcessor;
                }),
                Case($(instanceOf(InputException.class)), e -> {
                    ErrorProcessor errorProcessor = ErrorProcessor.builder()
                            .message(e.getMessage())
                            .build();
                    log.error("Error encountered at Input, input: "+errorProcessor);
                    return errorProcessor;
                }),
                Case($(instanceOf(PasswordException.class)), e -> {
                    ErrorProcessor errorProcessor = ErrorProcessor.builder()
                            .message(e.getMessage())
                            .build();
                    log.error("Error encountered at Password, input: "+errorProcessor);
                    return errorProcessor;
                }),
                Case($(instanceOf(RuntimeException.class)), e -> {
                    ErrorProcessor errorProcessor = ErrorProcessor.builder()
                            .message(e.getMessage())
                            .build();
                    log.error("Error encountered at Runtime, input: "+errorProcessor);
                    return errorProcessor;
                })
                );
    }
}
