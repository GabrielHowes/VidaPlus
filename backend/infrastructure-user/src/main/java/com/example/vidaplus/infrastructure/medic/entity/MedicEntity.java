package com.example.vidaplus.infrastructure.medic.entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "doctor")
@Data
public class MedicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "job_title", length = 80)
    private String jobTitle;

    @Column(name = "specialty", length = 100)
    private String specialty;

    @Column(name = "crm", nullable = false, length = 20)
    private String crm;

    @Column(name = "cpf", nullable = false, length = 20)
    private String cpf;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
