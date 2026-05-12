package com.example.vidaplus.domain.user.model;

import lombok.Builder;


@Builder
public record UserInput(
        String username,
        String password
) {
}
