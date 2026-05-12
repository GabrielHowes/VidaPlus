package com.example.vidaplus.controller.v1.admininstrador.request;

import lombok.Builder;


@Builder
public record AdministradorRequest(
        String nome,
        String email,
        String cpf,
        String cargo
) {
}
