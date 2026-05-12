package com.example.vidaplus.domain.websocket.model;

import lombok.Builder;


import java.time.LocalDateTime;


@Builder
public record RoomDto(
        Long id,
        String name,
        LocalDateTime createdAt
) {
}
