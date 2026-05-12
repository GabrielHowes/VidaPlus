package com.example.vidaplus.domain.administrador.model;

import lombok.Builder;

@Builder
public record AdministradorInput(
        String nome,
        String email,
        String cpf,
        String cargo
) {
}