package com.example.vidaplus.controller.v1.medic.response;

import lombok.Builder;


import java.time.LocalDateTime;


@Builder
public record MedicResponse(
        Long id,
        String name,
        String email,
        String jobTitle,
        String specialty,
        String crm,
        String cpf,
        LocalDateTime createdAt
) {
}