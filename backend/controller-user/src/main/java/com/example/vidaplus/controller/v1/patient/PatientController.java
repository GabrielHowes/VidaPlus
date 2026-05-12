package com.example.vidaplus.controller.v1.patient;

import com.example.vidaplus.controller.v1.patient.mapper.PatientMapperController;
import com.example.vidaplus.controller.v1.patient.request.PatientRequest;
import com.example.vidaplus.controller.v1.patient.response.PatientResponse;
import com.example.vidaplus.domain.patient.port.PatientApiPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/paciente")
@RequiredArgsConstructor
@Tag(name = "Pacientes", description = "Gerenciamento de pacientes")
@SecurityRequirement(name = "bearerAuth")
public class PatientController {

    private final PatientApiPort patientApiPort;
    private final PatientMapperController mapper;

    @Operation(summary = "Cadastrar paciente", description = "Cadastra um novo paciente no sistema")
    @PostMapping("/cadastro")
    public PatientResponse cadastraPaciente(
            @RequestBody PatientRequest request
    ){
        return mapper.toResponse(patientApiPort.create(mapper.toInput(request)));
    }

    @Operation(summary = "Listar pacientes", description = "Retorna uma lista com todos os pacientes cadastrados")
    @GetMapping
    public List<PatientResponse> listaPacientes(){
        return patientApiPort.listAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar paciente por CPF", description = "Retorna os detalhes de um paciente específico pelo CPF")
    @GetMapping("/{cpf}")
    public PatientResponse encontraPaciente(
            @PathVariable String cpf
    ){
        return mapper.toResponse(patientApiPort.findByCpf(cpf));
    }

    @Operation(summary = "Atualizar paciente", description = "Atualiza os dados de um paciente existente")
    @PutMapping("/{cpf}")
    public PatientResponse atualizaPaciente(
            @RequestBody PatientRequest request,
            @PathVariable String cpf
    ){
        return mapper.toResponse(patientApiPort.update(mapper.toInput(request), cpf));
    }

    @Operation(summary = "Deletar paciente", description = "Remove um paciente do sistema pelo CPF")
    @DeleteMapping("/{cpf}")
    public String deletePaciente(
            @PathVariable String cpf
    ){
        return patientApiPort.delete(cpf);
    }

}
