package com.seanhuvaya.quickpoll.coreapi.polls;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PollReadDto {
    private UUID id;
    private String question;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private Set<String> options;
}
