package com.example.vidaplus.domain.appointment.model;

import lombok.Builder;


import java.time.Instant;
import java.time.LocalDateTime;


@Builder
public record AppointmentDto(
        Long id,
        String patient,
        Long doctorId,
        LocalDateTime appointmentDate,
        String type,
        String status,
        String notes
) {
}