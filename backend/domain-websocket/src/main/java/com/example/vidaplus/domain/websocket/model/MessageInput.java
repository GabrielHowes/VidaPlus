package com.example.vidaplus.domain.websocket.model;

import lombok.Builder;


@Builder
public record MessageInput(
        String content,
        String username,
        String role,
        Long roomId
) {
}
