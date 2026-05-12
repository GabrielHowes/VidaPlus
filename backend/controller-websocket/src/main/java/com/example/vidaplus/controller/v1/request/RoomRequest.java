package com.example.vidaplus.controller.v1.request;

import jakarta.validation.constraints.NotBlank;


public record RoomRequest(
        @NotBlank
        String name
)  {
}
