package com.seanhuvaya.quickpoll.coreapi.polls;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PollServiceTest {
    @Mock
    private PollMapper pollMapper;

    @Mock
    private PollRepository pollRepository;

    @InjectMocks
    private PollService pollService;

    private Poll poll;
    private PollCreateDto pollCreateDto;
    private PollReadDto pollReadDto;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        this.poll = Poll.builder()
                .id(UUID.randomUUID())
                .question("What is your favorite programming language?")
                .options(Set.of("Java", "Python"))
                .expiresAt(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .build();

        this.pollCreateDto = PollCreateDto.builder()
                .question(this.poll.getQuestion())
                .options(this.poll.getOptions())
                .expiresAt(this.poll.getExpiresAt())
                .build();

        this.pollReadDto = PollReadDto.builder()
                .id(this.poll.getId())
                .question(this.poll.getQuestion())
                .options(this.poll.getOptions())
                .expiresAt(this.poll.getExpiresAt())
                .createdAt(this.poll.getCreatedAt())
                .build();

        when(pollMapper.fromCreateDto(this.pollCreateDto)).thenReturn(this.poll);
        when(pollMapper.toReadDto(this.poll)).thenReturn(this.pollReadDto);
    }

    @Test
    void testCreatePoll() {
        when(pollRepository.save(this.poll)).thenReturn(this.poll);

        PollReadDto pollReadDto = this.pollService.createPoll(this.pollCreateDto);

        assertThat(pollReadDto).isNotNull();
        assertThat(pollReadDto.getQuestion()).isEqualTo(this.pollReadDto.getQuestion());
        assertThat(pollReadDto.getOptions()).containsExactlyElementsOf(this.pollReadDto.getOptions());

        verify(pollRepository).save(this.poll);
        verify(pollMapper).fromCreateDto(this.pollCreateDto);
        verify(pollMapper).toReadDto(this.poll);
    }

    @Test
    void testGetPolls() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Poll> pollsPage = new PageImpl<>(List.of(poll));
        when(pollRepository.findAll(pageable)).thenReturn(pollsPage);

        Page<PollReadDto> result = pollService.getPolls(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getQuestion()).isEqualTo(this.poll.getQuestion());

        verify(pollRepository).findAll(pageable);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }
}