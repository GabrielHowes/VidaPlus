package com.example.vidaplus.domain.user.model;

import lombok.Builder;


@Builder
public record UserDto(
        String accessToken
) {
}
