package com.seanhuvaya.quickpoll.coreapi.polls;

import org.springframework.stereotype.Component;

@Component
public class PollMapper {
    Poll fromCreateDto(PollCreateDto pollCreateDto) {
        return Poll.builder()
                .question(pollCreateDto.getQuestion())
                .options(pollCreateDto.getOptions())
                .expiresAt(pollCreateDto.getExpiresAt())
                .build();
    }

    PollReadDto toReadDto(Poll poll) {
        return PollReadDto.builder()
                .id(poll.getId())
                .question(poll.getQuestion())
                .options(poll.getOptions())
                .expiresAt(poll.getExpiresAt())
                .createdAt(poll.getCreatedAt())
                .build();
    }
}
