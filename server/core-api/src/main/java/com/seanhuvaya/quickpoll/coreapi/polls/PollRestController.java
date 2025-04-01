package com.seanhuvaya.quickpoll.coreapi.polls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/polls")
public class PollRestController {
    private final PollService pollService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PollReadDto createPoll(@Validated @RequestBody PollCreateDto pollCreateDto) {
        return pollService.createPoll(pollCreateDto);
    }

    @GetMapping
    public Page<PollReadDto> getPolls(@PageableDefault Pageable pageable) {
        return pollService.getPolls(pageable);
    }
}
