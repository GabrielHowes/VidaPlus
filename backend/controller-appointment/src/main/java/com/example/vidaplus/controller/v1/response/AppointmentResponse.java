package com.example.vidaplus.controller.v1.response;

import lombok.Builder;


import java.time.Instant;
import java.time.LocalDateTime;


@Builder
public record AppointmentResponse(
        Long id,
        Long patientId,
        Long doctorId,
        LocalDateTime appointmentDate,
        String type,
        String status,
        String notes,
        LocalDateTime createdAt
) {
}