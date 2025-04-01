package com.seanhuvaya.quickpoll.coreapi.polls;


import com.seanhuvaya.quickpoll.coreapi.config.TestContainersConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
@Import(TestContainersConfig.class)
class PollRepositoryTest {

    @Autowired
    private PollRepository pollRepository;

    @Test
    void testIdIsSet() {
        Poll poll = Poll.builder()
                .question("Best Java framework?")
                .expiresAt(LocalDateTime.now().plusDays(1L))
                .options(Set.of("Spring", "Quarkus"))
                .build();

        Poll persistedPoll = pollRepository.saveAndFlush(poll);

        assertThat(persistedPoll).isNotNull();
        assertThat(persistedPoll.getId()).isNotNull();
    }

    @Test
    void testCreationTimestampIsSet() {
        Poll poll = Poll.builder()
                .question("Best Java framework?")
                .expiresAt(LocalDateTime.now().plusDays(1L))
                .options(Set.of("Spring", "Quarkus"))
                .build();

        Poll persistedPoll = pollRepository.saveAndFlush(poll);

        assertThat(persistedPoll).isNotNull();
        assertThat(persistedPoll.getCreatedAt()).isNotNull();
        assertThat(persistedPoll.getCreatedAt()).isBeforeOrEqualTo(LocalDateTime.now());
    }
}