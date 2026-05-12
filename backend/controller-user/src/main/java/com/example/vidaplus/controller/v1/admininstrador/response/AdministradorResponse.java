package com.example.vidaplus.controller.v1.admininstrador.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record AdministradorResponse(
        Long id,
        String nome,
        String email,
        String cpf,
        String cargo,
        LocalDateTime createdAt
) {
}