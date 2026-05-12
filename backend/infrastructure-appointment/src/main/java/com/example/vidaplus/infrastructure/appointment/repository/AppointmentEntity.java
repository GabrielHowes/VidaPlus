package com.example.vidaplus.infrastructure.appointment.repository;

import jakarta.persistence.*;
import lombok.*;


import java.time.Instant;
import java.time.LocalDateTime;


@Entity
@Table(name = "appointment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    @Column(name = "doctor_id", nullable = false)
    private Long doctorId;

    @Column(name = "appointment_date", nullable = false)
    private LocalDateTime appointmentDate;

    @Column(length = 50)
    private String type;

    @Column(length = 20)
    private String status;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
