package com.example.vidaplus.controller.v1.medic;

import com.example.vidaplus.controller.v1.medic.mapper.MedicMapperController;
import com.example.vidaplus.controller.v1.medic.request.MedicRequest;
import com.example.vidaplus.controller.v1.medic.response.MedicResponse;
import com.example.vidaplus.domain.medic.port.MedicApiPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/medic")
@RequiredArgsConstructor
@Tag(name = "Médicos", description = "Gerenciamento de médicos")
@SecurityRequirement(name = "bearerAuth")
public class MedicController {

    private final MedicApiPort medicApiPort;
    private final MedicMapperController mapper;

    @Operation(summary = "Cadastrar médico", description = "Cadastra um novo médico no sistema")
    @PostMapping("/cadastro")
    public MedicResponse registerMedic(
            @RequestBody MedicRequest request
    ){
        return mapper.toResponse(
                medicApiPort.create(
                        mapper.toInput(request)
                )
        );
    }

    @Operation(summary = "Listar médicos", description = "Retorna uma lista com todos os médicos cadastrados")
    @GetMapping
    public List<MedicResponse> listMedics(){
        return medicApiPort.listAll().stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Buscar médico por CRM", description = "Retorna os detalhes de um médico específico pelo CRM")
    @GetMapping("/{crm}")
    public MedicResponse findMedicByCrm(
            @PathVariable String crm
    ){
        return mapper.toResponse(medicApiPort.findByCrm(crm));
    }

    @Operation(summary = "Atualizar médico", description = "Atualiza os dados de um médico existente")
    @PutMapping("/{crm}")
    public MedicResponse updateMedic(
            @RequestBody MedicRequest request,
            @PathVariable String crm
    ){
        return mapper.toResponse(
                medicApiPort.update(
                        mapper.toInput(request), crm)
        );
    }

    @Operation(summary = "Deletar médico", description = "Remove um médico do sistema pelo CRM")
    @DeleteMapping("/{crm}")
    public String deleteMedico(
            @PathVariable String crm
    ){
        return medicApiPort.delete(crm);
    }

}
