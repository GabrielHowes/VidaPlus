package com.example.vidaplus.domain.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;


public record TokenDto(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expires_in") int expiresIn,
        @JsonProperty("refresh_token") String refreshToken,
        @JsonProperty("token_type") String tokenType
) {
}
