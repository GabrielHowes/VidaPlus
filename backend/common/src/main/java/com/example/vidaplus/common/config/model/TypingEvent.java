package com.example.vidaplus.common.config.model;

import lombok.Builder;


@Builder
public record TypingEvent(
        String username,
        Long roomId,
        Boolean typing) {
}
