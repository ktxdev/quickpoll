package com.seanhuvaya.quickpoll.coreapi.polls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PollExpiresAtLocalDateTimeDeserializerTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.addMixIn(LocalDateTime.class, LocalDateTimeMixin.class);
    }

    @Test
    void testDeserialize_withValidNumber() throws JsonProcessingException {
        final String json = "10";
        LocalDateTime expectedDateTime = LocalDateTime.now().plusMinutes(10);

        LocalDateTime actualDateTime = objectMapper.readValue(json, LocalDateTime.class);

        assertNotNull(actualDateTime);
        assertTrue(actualDateTime.isAfter(expectedDateTime.minusSeconds(2)) && actualDateTime.isBefore(expectedDateTime.plusSeconds(2)));
    }

    @Test
    void testDeserialize_withInvalidNumber() {
        final String json = "invalid";

        assertThrows(JsonProcessingException.class, () -> objectMapper.readValue(json, LocalDateTime.class));
    }

    @Test
    void testDeserialize_withNullString() throws JsonProcessingException {
        final String json = "null";

        assertNull(objectMapper.readValue(json, LocalDateTime.class));
    }

}