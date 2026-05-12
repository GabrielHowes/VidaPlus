package com.example.vidaplus.domain.websocket.model;

import lombok.Builder;


@Builder
public record RoomInput(
        String name
) {
}
