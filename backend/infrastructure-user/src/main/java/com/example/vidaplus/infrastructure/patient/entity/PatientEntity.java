package com.example.vidaplus.infrastructure.patient.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
@Entity
@Table(name = "patient")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "email", nullable = false, length = 120, unique = true)
    private String email;

    @Column(name = "cpf", nullable = false, length = 14)
    private String cpf;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
