package com.seanhuvaya.quickpoll.coreapi.polls;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Poll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String question;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;

    @ElementCollection
    @Size(min = 2, max = 4, message = "A poll must have between 2 and 4 options")
    private Set<String> options;
}
