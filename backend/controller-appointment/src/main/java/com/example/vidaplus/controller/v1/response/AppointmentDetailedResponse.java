package com.example.vidaplus.controller.v1.response;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record AppointmentDetailedResponse(
        Long id,
        String patientName,
        LocalDateTime appointmentDate,
        String doctorName,
        String status,
        String notes
) {
}
