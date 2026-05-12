package com.example.vidaplus.infrastructure.administrator.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "administrator")
@Data
public class AdministratorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "cardId", nullable = false, length = 80)
    private String cardId;

    @Column(name = "job_title", length = 50)
    private String jobTitle;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }


}
