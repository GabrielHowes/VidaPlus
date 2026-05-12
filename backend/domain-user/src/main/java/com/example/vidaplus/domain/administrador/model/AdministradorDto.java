package com.example.vidaplus.domain.administrador.model;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record AdministradorDto(
        Long id,
        String nome,
        String email,
        String cpf,
        String cargo,
        LocalDateTime createdAt
) {
}