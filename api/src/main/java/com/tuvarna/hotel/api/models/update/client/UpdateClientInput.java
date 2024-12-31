package com.tuvarna.hotel.api.models.update.client;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.enums.RatingClient;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateClientInput implements OperationInput{
    private UUID client;
    private RatingClient ratingClient;
}
