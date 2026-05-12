package com.example.vidaplus.controller.v1.request;

import lombok.Builder;


import java.time.LocalDateTime;


@Builder
public record AppointmentRequest(
        Long doctorId,
        LocalDateTime appointmentDate,
        String type,
        String status,
        String notes
) {
}