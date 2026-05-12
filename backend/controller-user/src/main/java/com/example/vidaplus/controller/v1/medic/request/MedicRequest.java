package com.example.vidaplus.controller.v1.medic.request;

import lombok.Builder;


@Builder
public record MedicRequest(
        String name,
        String email,
        String jobTitle,
        String specialty,
        String crm,
        String cpf
) {
}