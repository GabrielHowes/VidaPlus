package com.example.vidaplus.controller.v1.request;

import jakarta.validation.constraints.NotBlank;


public record MessageRequest(
        @NotBlank
        String roomId,
        @NotBlank
        String username,
        @NotBlank
        String message
)  {
}
