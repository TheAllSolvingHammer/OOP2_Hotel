package com.tuvarna.hotel.api.exceptions;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class ErrorProcessor {
    private String message;
}
