package com.tuvarna.hotel.core.converters;

import com.tuvarna.hotel.api.enums.RatingClient;
import com.tuvarna.hotel.api.models.entities.Client;
import com.tuvarna.hotel.domain.singleton.Singleton;
import com.tuvarna.hotel.persistence.entities.ClientEntity;

import java.util.List;

@Singleton
public class ConvertEntityToClient implements BaseConverter<List<ClientEntity>,List<Client>> {
    @Override
    public List<Client> convert(List<ClientEntity> clientEntities) {
        return clientEntities.stream().map(clientEntity -> Client.builder()
                        .id(clientEntity.getId())
                        .address(clientEntity.getAddress())
                        .birthDate(clientEntity.getBirthDate())
                        .email(clientEntity.getEmail())
                        .expireDate(clientEntity.getExpireDate())
                        .firstName(clientEntity.getFirstName())
                        .lastName(clientEntity.getLastName())
                        .issueDate(clientEntity.getIssueDate())
                        .issuedBy(clientEntity.getIssuedBy())
                        .phone(clientEntity.getPhone())
                        .ucn(clientEntity.getUcn())
                        .rating(RatingClient.getByCode(clientEntity.getRating().name()))
                        .build())
                .toList();
    }

}
