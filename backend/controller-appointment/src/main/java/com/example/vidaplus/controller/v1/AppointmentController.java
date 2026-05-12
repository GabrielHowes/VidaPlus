package com.example.vidaplus.controller.v1;

import com.example.vidaplus.controller.v1.mapper.AppointmentMapperContract;
import com.example.vidaplus.controller.v1.request.AppointmentRequest;
import com.example.vidaplus.controller.v1.response.AppointmentDetailedResponse;
import com.example.vidaplus.controller.v1.response.AppointmentResponse;
import com.example.vidaplus.domain.appointment.port.AppointmentApiPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/v1/appointment")
@RequiredArgsConstructor
@Tag(name = "Consultas", description = "Gerenciamento de consultas médicas")
@SecurityRequirement(name = "bearerAuth")
public class AppointmentController {

    private final AppointmentApiPort appointmentApiPort;
    private final AppointmentMapperContract mapper;

    @Operation(summary = "Agendar consulta", description = "Agenda uma nova consulta médica")
    @PostMapping
    public AppointmentResponse appointmentAdd(
            @RequestBody AppointmentRequest request,
            @AuthenticationPrincipal Jwt jwt
    ){
        return mapper.toDomain(
                appointmentApiPort.appointmentAdd(
                        mapper.toDomain(request), jwt.getClaim("email")
                )
        );
    }

    @Operation(summary = "Listar consultas", description = "Retorna uma lista com todas as consultas agendadas")
    @GetMapping
    public List<AppointmentResponse> appointmentList(){
        return mapper.toDomain(appointmentApiPort.appointmentList());
    }

    @Operation(summary = "Listar consultas detalhadas", description = "Retorna informações detalhadas das consultas (paciente, médico, data e status)")
    @GetMapping("/detailed-list")
    public List<AppointmentDetailedResponse> appointmentDetailedList(){
        return mapper.toDetailed(appointmentApiPort.appointmentDetailedList());
    }

    @Operation(summary = "Lista consultas por usuário", description = "Retorna Lista de medicos e usuários com consultas disponiveis")
    @GetMapping("/list-by-user")
    public List<AppointmentResponse> appointmentListById(@AuthenticationPrincipal Jwt jwt){
        return mapper.toDomain(appointmentApiPort.appointmentListById(jwt.getClaim("email")));
    }

    @Operation(summary = "Buscar consulta por ID", description = "Retorna os detalhes de uma consulta específica")
    @GetMapping("/{appointmentId}")
    public AppointmentResponse appointmentFind(
            @PathVariable Long appointmentId
    ){
        return mapper.toDomain(appointmentApiPort.appointmentFind(appointmentId));
    }

    @Operation(summary = "Atualizar consulta", description = "Atualiza os dados de uma consulta existente")
    @PutMapping("/{appointmentId}")
    public AppointmentResponse appointmentUpdate(
            @RequestBody AppointmentRequest request,
            @PathVariable Long appointmentId
    ){
        return mapper.toDomain(
                appointmentApiPort.appointmentUpdate(
                        mapper.toDomain(request),
                        appointmentId)
        );
    }

    @Operation(summary = "Cancelar consulta", description = "Cancela uma consulta agendada")
    @DeleteMapping("/{appointmentId}")
    public String appointmentDelete(
            @PathVariable Long appointmentId
    ){
        return appointmentApiPort.appointmentDelete(appointmentId);
    }

    @Operation(summary = "Dashboard Mensal", description = "Retorna todas as consultas de um mês específico")
    @GetMapping("/dashboard/month")
    public List<AppointmentResponse> listByMonth(
            @RequestParam int year,
            @RequestParam int month
    ) {
        return mapper.toDomain(appointmentApiPort.listByMonth(year, month));
    }

}
