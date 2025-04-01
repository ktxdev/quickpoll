package com.seanhuvaya.quickpoll.coreapi.polls;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class PollDtoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void  testValidPollDto() {
        PollDto pollDto = PollDto.builder()
                .question("What's your favorite programming language?")
                .options(Set.of("Java", "Python"))
                .expiresAt(LocalDateTime.now().plusMinutes(1))
                .build();

        Set<ConstraintViolation<PollDto>> constraintViolations = validator.validate(pollDto);
        assertThat(constraintViolations).isEmpty();
    }

    @Test
    void  testPollDtoWithEmptyQuestion() {
        PollDto pollDto = PollDto.builder()
                .question("")
                .options(Set.of("Java", "Python"))
                .expiresAt(LocalDateTime.now().plusMinutes(1))
                .build();

        Set<ConstraintViolation<PollDto>> constraintViolations = validator.validate(pollDto);
        assertThat(constraintViolations).isNotEmpty();
        assertThat(constraintViolations).anyMatch(violation -> violation.getMessage().equals("Question should be provided"));
    }

    @Test
    void  testPollDtoWithNullExpiresAt() {
        PollDto pollDto = PollDto.builder()
                .question("What's your favorite programming language?")
                .options(Set.of("Java", "Python"))
                .expiresAt(null)
                .build();

        Set<ConstraintViolation<PollDto>> constraintViolations = validator.validate(pollDto);
        assertThat(constraintViolations).isNotEmpty();
        assertThat(constraintViolations).anyMatch(violation -> violation.getMessage().equals("Expiry time in minutes is required"));
    }

    @Test
    void  testPollDtoWithFewOptions() {
        PollDto pollDto = PollDto.builder()
                .question("What's your favorite programming language?")
                .options(Set.of("Java"))
                .expiresAt(LocalDateTime.now().plusMinutes(1))
                .build();

        Set<ConstraintViolation<PollDto>> constraintViolations = validator.validate(pollDto);
        assertThat(constraintViolations).isNotEmpty();
        assertThat(constraintViolations).anyMatch(violation -> violation.getMessage().equals("A poll must have between 2 and 4 options"));
    }

    @Test
    void  testPollDtoWithMoreOptions() {
        PollDto pollDto = PollDto.builder()
                .question("What's your favorite programming language?")
                .options(Set.of("Java", "Python", "Ruby", "C#", "C++"))
                .expiresAt(LocalDateTime.now().plusMinutes(1))
                .build();

        Set<ConstraintViolation<PollDto>> constraintViolations = validator.validate(pollDto);
        assertThat(constraintViolations).isNotEmpty();
        assertThat(constraintViolations).anyMatch(violation -> violation.getMessage().equals("A poll must have between 2 and 4 options"));
    }
}