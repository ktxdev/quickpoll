package com.seanhuvaya.quickpoll.coreapi.polls;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = PollExpiresAtLocalDateTimeDeserializer.class)
interface LocalDateTimeMixin {}
