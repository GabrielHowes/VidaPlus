package com.example.vidaplus.controller.v1.patient.request;

import lombok.Builder;


import java.time.LocalDate;


@Builder
public record PatientRequest(
        String name,
        String cpf,
        String email,
        LocalDate birthDate
) {
}