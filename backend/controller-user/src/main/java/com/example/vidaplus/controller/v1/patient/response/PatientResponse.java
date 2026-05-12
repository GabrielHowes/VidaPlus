package com.example.vidaplus.controller.v1.patient.response;

import lombok.Builder;


import java.time.LocalDate;


@Builder
public record PatientResponse(
        String name,
        String cpf,
        String email,
        LocalDate birthDate
) {
}
