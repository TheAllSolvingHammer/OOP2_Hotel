package com.tuvarna.hotel.api.models.update.receptionist;

import com.tuvarna.hotel.api.base.OperationInput;
import com.tuvarna.hotel.api.models.entities.Receptionist;
import lombok.*;

import java.util.List;
import java.util.UUID;
@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class UpdateReceptionistOfHotelInput implements OperationInput {
    private UUID hotelID;
    private List<Receptionist> receptionistList;
}
