package com.tuvarna.hotel.api.models.query.client.information;

import com.tuvarna.hotel.api.base.OperationOutput;
import com.tuvarna.hotel.api.models.entities.Client;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class ClientInformationOutput implements OperationOutput {
    private List<Client> clientList;
}
