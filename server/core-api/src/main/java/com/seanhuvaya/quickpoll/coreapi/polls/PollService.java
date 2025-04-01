package com.seanhuvaya.quickpoll.coreapi.polls;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PollService {
    PollReadDto createPoll(PollCreateDto pollCreateDto) {
        Poll poll = Poll.builder()
                .question(pollCreateDto.getQuestion())
                .options(pollCreateDto.getOptions())
                .expiresAt(pollCreateDto.getExpiresAt())
                .build();
        return null;
    }

    Page<PollReadDto> getPolls(Pageable pageable) {
        return null;
    }
}
