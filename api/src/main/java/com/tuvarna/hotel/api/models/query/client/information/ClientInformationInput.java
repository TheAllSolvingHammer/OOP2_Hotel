package com.tuvarna.hotel.api.models.query.client.information;

import com.tuvarna.hotel.api.base.OperationInput;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@Builder
public class ClientInformationInput implements OperationInput {

}
