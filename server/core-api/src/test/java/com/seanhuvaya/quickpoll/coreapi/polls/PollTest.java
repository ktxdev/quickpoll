package com.seanhuvaya.quickpoll.coreapi.polls;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PollTest {

    private final Validator validator;

    public PollTest() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    void testValidPollOptions() {
        Poll poll = Poll.builder()
                .id(UUID.randomUUID())
                .question("What's your favorite programming language?")
                .options(Set.of("Java", "C#", "Python"))
                .expiresAt(LocalDateTime.now().plusDays(1L))
                .createdAt(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<Poll>> violations = validator.validate(poll);
        assertThat(violations).isEmpty();
    }

    @Test
    void testPollWithTooFewOptions() {
        Poll poll = Poll.builder()
                .id(UUID.randomUUID())
                .question("What's your favorite programming language?")
                .options(Set.of("Java"))
                .expiresAt(LocalDateTime.now().plusDays(1L))
                .createdAt(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<Poll>> violations = validator.validate(poll);
        assertThat(violations).isNotEmpty();
    }

    @Test
    void testPollWithTooManyOptions() {
        Poll poll = Poll.builder()
                .id(UUID.randomUUID())
                .question("What's your favorite programming language?")
                .options(Set.of("Java", "C#", "Python", "Go", "JavaScript"))
                .expiresAt(LocalDateTime.now().plusDays(1L))
                .createdAt(LocalDateTime.now())
                .build();

        Set<ConstraintViolation<Poll>> violations = validator.validate(poll);
        assertThat(violations).isNotEmpty();
    }

}