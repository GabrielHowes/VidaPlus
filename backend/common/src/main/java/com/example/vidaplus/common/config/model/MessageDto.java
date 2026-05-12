package com.example.vidaplus.common.config.model;

import lombok.Builder;


import java.time.LocalDateTime;


@Builder
public record MessageDto(
        Long id,
        String content,
        String username,
        String role,
        Long roomId,
        LocalDateTime createdAt
) {
}
