package com.seanhuvaya.quickpoll.coreapi.polls;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class PollExpiresAtLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        try {
            long value = Long.parseLong(p.getText());
            return LocalDateTime.now().plusMinutes(value);
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
