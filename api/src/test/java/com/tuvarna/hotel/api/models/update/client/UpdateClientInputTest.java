package com.tuvarna.hotel.api.models.update.client;

import static org.junit.jupiter.api.Assertions.*;

import com.tuvarna.hotel.api.enums.RatingClient;
import org.junit.jupiter.api.Test;
import java.util.UUID;

public class UpdateClientInputTest {

    @Test
    public void testConstructorAndGettersSetters() {
        UUID client = UUID.randomUUID();
        RatingClient ratingClient = RatingClient.NORMAL;

        UpdateClientInput input = new UpdateClientInput(client, ratingClient);

        assertEquals(client, input.getClient());
        assertEquals(ratingClient, input.getRatingClient());

        UUID newClient = UUID.randomUUID();
        input.setClient(newClient);
        assertEquals(newClient, input.getClient());

        RatingClient newRatingClient = RatingClient.CONCERNING;
        input.setRatingClient(newRatingClient);
        assertEquals(newRatingClient, input.getRatingClient());
    }

    @Test
    public void testToString() {
        UUID client = UUID.randomUUID();
        RatingClient ratingClient = RatingClient.NORMAL;

        UpdateClientInput input = new UpdateClientInput(client, ratingClient);

        String toStringOutput = input.toString();
        assertTrue(toStringOutput.contains("client=" + client));
        assertTrue(toStringOutput.contains("ratingClient=" + ratingClient));
    }

    @Test
    public void testBuilder() {
        UUID client = UUID.randomUUID();
        RatingClient ratingClient = RatingClient.NORMAL;

        UpdateClientInput input = UpdateClientInput.builder()
                .client(client)
                .ratingClient(ratingClient)
                .build();

        assertEquals(client, input.getClient());
        assertEquals(ratingClient, input.getRatingClient());
    }

    @Test
    public void testNoArgsConstructor() {
        UpdateClientInput input = new UpdateClientInput();
        assertNull(input.getClient());
        assertNull(input.getRatingClient());
    }
}
