package com.seanhuvaya.quickpoll.coreapi.polls;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PollCreateDto {
    @NotEmpty(message = "Question should be provided")
    private String question;
    @NotNull(message = "Expiry time in minutes is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "expires_in_minutes", required = true)
    @JsonDeserialize(using = PollExpiresAtLocalDateTimeDeserializer.class)
    private LocalDateTime expiresAt;
    @Size(min = 2, max = 4, message = "A poll must have between 2 and 4 options")
    private Set<String> options;
}
