package com.seanhuvaya.quickpoll.coreapi.polls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PollService {
    private final PollMapper pollMapper;
    private final PollRepository pollRepository;

    PollReadDto createPoll(PollCreateDto pollCreateDto) {
        Poll poll = pollMapper.fromCreateDto(pollCreateDto);
        Poll persistedPoll = pollRepository.save(poll);
        return pollMapper.toReadDto(persistedPoll);
    }

    Page<PollReadDto> getPolls(Pageable pageable) {
        return pollRepository.findAll(pageable)
                .map(pollMapper::toReadDto);
    }
}
