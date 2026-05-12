package com.example.vidaplus.domain.appointment.model;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record AppointmentDetailedDto(
        Long id,
        String patientName,
        LocalDateTime appointmentDate,
        String doctorName,
        String status,
        String notes
) {
}
