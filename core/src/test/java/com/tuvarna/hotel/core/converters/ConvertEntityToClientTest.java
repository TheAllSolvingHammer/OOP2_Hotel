package com.tuvarna.hotel.core.converters;

import static org.junit.jupiter.api.Assertions.*;

import com.tuvarna.hotel.api.enums.RatingClient;
import com.tuvarna.hotel.api.models.entities.Client;
import com.tuvarna.hotel.persistence.entities.ClientEntity;
import com.tuvarna.hotel.persistence.enums.ClientRating;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class ConvertEntityToClientTest {

    private final ConvertEntityToClient converter = new ConvertEntityToClient();

    @Test
    public void testConvert() {

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(UUID.randomUUID());
        clientEntity.setAddress("Varna Varna");
        clientEntity.setBirthDate(LocalDate.parse("1990-01-01"));
        clientEntity.setEmail("krisevans@gmail.com");
        clientEntity.setExpireDate(LocalDate.parse("2025-01-01"));
        clientEntity.setFirstName("Chris");
        clientEntity.setLastName("Evans");
        clientEntity.setIssueDate(LocalDate.parse("2023-01-01"));
        clientEntity.setIssuedBy("Admin");
        clientEntity.setPhone("1234567890");
        clientEntity.setUcn("123456789012");
        clientEntity.setRating(ClientRating.NORMAL);

        List<ClientEntity> clientEntityList = List.of(clientEntity);

        List<Client> clientList = converter.convert(clientEntityList);

        assertNotNull(clientList);
        assertEquals(1, clientList.size());

        Client client = clientList.getFirst();
        assertEquals(clientEntity.getId(), client.getId());
        assertEquals(clientEntity.getAddress(), client.getAddress());
        assertEquals(clientEntity.getBirthDate(), client.getBirthDate());
        assertEquals(clientEntity.getEmail(), client.getEmail());
        assertEquals(clientEntity.getExpireDate(), client.getExpireDate());
        assertEquals(clientEntity.getFirstName(), client.getFirstName());
        assertEquals(clientEntity.getLastName(), client.getLastName());
        assertEquals(clientEntity.getIssueDate(), client.getIssueDate());
        assertEquals(clientEntity.getIssuedBy(), client.getIssuedBy());
        assertEquals(clientEntity.getPhone(), client.getPhone());
        assertEquals(clientEntity.getUcn(), client.getUcn());
        assertEquals(RatingClient.getByCode(clientEntity.getRating().name()), client.getRating());
    }

    @Test
    public void testConvertEmptyList() {
        List<Client> clientList = converter.convert(List.of());
        assertNotNull(clientList);
        assertTrue(clientList.isEmpty());
    }
}