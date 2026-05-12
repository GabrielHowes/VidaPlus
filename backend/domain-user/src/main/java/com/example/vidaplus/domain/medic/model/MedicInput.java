package com.example.vidaplus.domain.medic.model;

import lombok.Builder;


@Builder
public record MedicInput(
        String name,
        String email,
        String jobTitle,
        String specialty,
        String crm,
        String cpf
) {
}
