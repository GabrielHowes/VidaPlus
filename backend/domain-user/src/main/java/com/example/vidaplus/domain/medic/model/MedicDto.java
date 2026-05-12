package com.example.vidaplus.domain.medic.model;

import lombok.Builder;


import java.time.LocalDateTime;


@Builder
public record MedicDto(
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
