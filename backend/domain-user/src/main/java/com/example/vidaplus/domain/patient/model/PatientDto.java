package com.example.vidaplus.domain.patient.model;

import lombok.Builder;


import java.time.LocalDate;


@Builder
public record PatientDto(
        String name,
        String cpf,
        String email,
        LocalDate birthDate
) {
}
