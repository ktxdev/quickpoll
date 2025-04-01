package com.seanhuvaya.quickpoll.coreapi.polls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(PollRestController.class)
class PollRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PollService pollService;

    private PollCreateDto pollCreateDto;
    private PollReadDto pollReadDto;

    private AutoCloseable closeable;

    @Test
    void contextLoads() {
        assertNotNull(mockMvc); // Simple sanity check
    }

    @BeforeEach
    void setUp() {

        this.pollCreateDto = PollCreateDto.builder()
                .question("What is your favorite programming language?")
                .options(Set.of("Python","Java"))
                .expiresAt(LocalDateTime.now().plusDays(1))
                .build();

        this.pollReadDto = PollReadDto.builder()
                .id(UUID.randomUUID())
                .question(this.pollCreateDto.getQuestion())
                .options(this.pollCreateDto.getOptions())
                .expiresAt(this.pollCreateDto.getExpiresAt())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void createPoll_ShouldReturnCreatedPoll() throws Exception {
        when(pollService.createPoll(any(PollCreateDto.class))).thenReturn(pollReadDto);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.valueToTree(pollCreateDto);
        jsonNode.put("expires_in_minutes", "60");

        mockMvc.perform(post("/api/v1/polls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNode.toString()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.question").value("What is your favorite programming language?"))
                .andExpect(jsonPath("$.options", containsInAnyOrder("Java", "Python")));

        verify(pollService, times(1)).createPoll(any(PollCreateDto.class));
    }

    @Test
    void getPolls_ShouldReturnPagedPolls() throws Exception {
        Page<PollReadDto> pollsPage = new PageImpl<>(List.of(pollReadDto));
        when(pollService.getPolls(any(Pageable.class))).thenReturn(pollsPage);

        mockMvc.perform(get("/api/v1/polls")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].question").value("What is your favorite programming language?"))
                .andExpect(jsonPath("$.content[0].options", containsInAnyOrder("Java", "Python")));

        verify(pollService, times(1)).getPolls(any(Pageable.class));
    }
}