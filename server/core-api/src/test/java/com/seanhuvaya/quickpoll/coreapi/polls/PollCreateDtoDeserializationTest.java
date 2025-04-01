package com.seanhuvaya.quickpoll.coreapi.polls;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PollCreateDtoDeserializationTest {

    private Validator validator;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        objectMapper = new ObjectMapper();
        objectMapper.addMixIn(LocalDateTime.class, LocalDateTimeMixin.class);
    }

    @Test
    void testDeserializePollDto() throws IOException {
        String json = "{ \"question\": \"Best framework?\", \"expires_in_minutes\": 20 }";

        PollCreateDto pollCreateDto = objectMapper.readValue(json, PollCreateDto.class);

        assertThat(pollCreateDto).isNotNull();
        assertThat(pollCreateDto.getQuestion()).isEqualTo("Best framework?");
        assertThat(pollCreateDto.getExpiresAt()).isAfter(LocalDateTime.now());
    }

    @Test
    void testDeserializePollDtoWithInvalidExpiresAt() throws JsonProcessingException {
        String json = "{ \"question\": \"Best framework?\", \"expires_in_minutes\": \"invalid\" }";

        PollCreateDto pollCreateDto = objectMapper.readValue(json, PollCreateDto.class);

        assertNull(objectMapper.readValue(json, LocalDateTime.class));
        Set<ConstraintViolation<PollCreateDto>> violations = validator.validate(pollCreateDto);

        assertThat(violations).isNotEmpty();
        assertThat(violations.size()).isEqualTo(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Expiry time in minutes is required");
    }

}