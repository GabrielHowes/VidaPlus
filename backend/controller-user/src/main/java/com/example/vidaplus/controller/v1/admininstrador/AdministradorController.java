package com.example.vidaplus.controller.v1.admininstrador;

import com.example.vidaplus.controller.v1.admininstrador.mapper.AdministradorMapperController;
import com.example.vidaplus.controller.v1.admininstrador.request.AdministradorRequest;
import com.example.vidaplus.controller.v1.admininstrador.response.AdministradorResponse;
import com.example.vidaplus.domain.administrador.port.AdministradorApiPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/v1/administrador")
@RequiredArgsConstructor
@Tag(name = "Administradores", description = "Gerenciamento de administradores")
@SecurityRequirement(name = "bearerAuth")
public class AdministradorController {

    private final AdministradorApiPort service;
    private final AdministradorMapperController mapper;

    @Operation(summary = "Cadastrar administrador", description = "Cadastra um novo administrador no sistema")
    @PostMapping("/cadastro")
    public AdministradorResponse cadastroAdmin(
            @RequestBody AdministradorRequest request
            ){
        return mapper.toResponse(
                service.cadastroAdmin(
                        mapper.toInput(request)
                )
        );
    }

    @Operation(summary = "Listar administradores", description = "Retorna uma lista com todos os administradores cadastrados")
    @GetMapping
    public List<AdministradorResponse> listaAdmins(){
        return service.listaAdmins().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Operation(summary = "Buscar administrador por CPF", description = "Retorna os detalhes de um administrador específico pelo CPF")
    @GetMapping("/{cpf}")
    public AdministradorResponse encontraAdmin(
            @PathVariable String cpf
    ){
        return mapper.toResponse(service.encontraAdmin(cpf));
    }

    @Operation(summary = "Atualizar administrador", description = "Atualiza os dados de um administrador existente")
    @PutMapping("/{cpf}")
    public AdministradorResponse atualizaAdmin(
            @RequestBody AdministradorRequest request,
            @PathVariable String cpf
    ){
        return mapper.toResponse(
                service.atualizarAdmin(
                        mapper.toInput(request), cpf
                )
        );
    }

    @Operation(summary = "Deletar administrador", description = "Remove um administrador do sistema pelo CPF")
    @DeleteMapping("/{cpf}")
    public String deleteAdmin(
            @PathVariable String cpf
    ){
        return service.deleteAdmin(cpf);
    }

}