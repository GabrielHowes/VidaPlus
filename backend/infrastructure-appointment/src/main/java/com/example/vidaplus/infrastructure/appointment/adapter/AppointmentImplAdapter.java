package com.example.vidaplus.infrastructure.appointment.adapter;

import com.example.vidaplus.domain.appointment.model.AppointmentDetailedDto;
import com.example.vidaplus.domain.appointment.model.AppointmentDto;
import com.example.vidaplus.domain.appointment.model.AppointmentInput;
import com.example.vidaplus.domain.appointment.port.AppointmentSpiPort;
import com.example.vidaplus.infrastructure.administrator.entity.AdministratorEntity;
import com.example.vidaplus.infrastructure.administrator.repository.AdministratorRepositoryHandler;
import com.example.vidaplus.infrastructure.appointment.repository.AppointmentEntity;
import com.example.vidaplus.infrastructure.appointment.repository.AppointmentMapper;
import com.example.vidaplus.infrastructure.appointment.repository.AppointmentRepositoryHandler;
import com.example.vidaplus.infrastructure.medic.entity.MedicEntity;
import com.example.vidaplus.infrastructure.medic.repository.MedicRepositoryHandler;
import com.example.vidaplus.infrastructure.patient.entity.PatientEntity;
import com.example.vidaplus.infrastructure.patient.repository.PatientRepositoryHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.time.YearMonth;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;


@Component
@Slf4j
@RequiredArgsConstructor
public class AppointmentImplAdapter implements AppointmentSpiPort {

    private final AppointmentRepositoryHandler repositoryHandler;
    private final PatientRepositoryHandler patientRepositoryHandler;
    private final MedicRepositoryHandler medicRepositoryHandler;
    private final AdministratorRepositoryHandler adminRepositoryHandler;
    private final Executor virtualThreadExecutor;
    private final AppointmentMapper mapper;

    @Override
    public AppointmentDto appointmentAdd(AppointmentInput input, String email) {
        var userExisting = patientRepositoryHandler.findUserByEmail(email);

        repositoryHandler.insertAppointment(input, userExisting);

        return mapper.toDto(input);

    }

    @Override
    public List<AppointmentDto> appointmentList() {
        return repositoryHandler.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDetailedDto> appointmentDetailedList() {
        return repositoryHandler.findAll().stream()
                .map(entity -> {
                    String patientName = Optional.ofNullable(patientRepositoryHandler.findById(entity.getPatientId()))
                            .flatMap(opt -> opt)
                            .map(PatientEntity::getName)
                            .orElse("Unknown Patient");

                    String doctorName = Optional.ofNullable(medicRepositoryHandler.findById(entity.getDoctorId()))
                            .flatMap(opt -> opt)
                            .map(MedicEntity::getName)
                            .orElse("Unknown Doctor");

                    return AppointmentDetailedDto.builder()
                            .id(entity.getId())
                            .patientName(patientName)
                            .appointmentDate(entity.getAppointmentDate())
                            .doctorName(doctorName)
                            .status(entity.getStatus())
                            .notes(entity.getNotes())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> appointmentListById(String email) {
        CompletableFuture<PatientEntity> patientFuture = CompletableFuture.supplyAsync(() ->
                patientRepositoryHandler.findUserByEmail(email), virtualThreadExecutor );

        CompletableFuture<MedicEntity> medicFuture = CompletableFuture.supplyAsync(() ->
                medicRepositoryHandler.findByEmail(email), virtualThreadExecutor );

        CompletableFuture<AdministratorEntity> adminFuture = CompletableFuture.supplyAsync(() ->
                adminRepositoryHandler.findByEmail(email), virtualThreadExecutor
                );

        if(patientFuture.join() != null)return mapper.toDto(repositoryHandler.listByPatientId(patientFuture.join().getId()));
        if(adminFuture.join() != null)return mapper.toDto(repositoryHandler.findAll());
        if(medicFuture.join() != null)return mapper.toDto(repositoryHandler.listByMedicId( medicFuture.join().getId()));

        return List.of();
    }

    @Override
    public AppointmentDto appointmentFind(Long appointmentId) {
        AppointmentEntity entity = repositoryHandler.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found."));
        return mapper.toDto(entity);
    }

    @Override
    public AppointmentDto appointmentUpdate(AppointmentInput input, Long appointmentId) {
        AppointmentEntity entity = repositoryHandler.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found."));

        mapper.updateEntityFromInput(input, entity);

        AppointmentEntity updatedEntity = repositoryHandler.save(entity);
        return mapper.toDto(updatedEntity);
    }

    @Override
    public String appointmentDelete(Long appointmentId) {
        return repositoryHandler.findById(appointmentId)
                .map(entity -> {
                    repositoryHandler.delete(entity);
                    return "Appointment deleted successfully!";
                })
                .orElseThrow(() -> new RuntimeException("Appointment not found."));
    }

    @Override
    public List<AppointmentDto> listByMonth(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        var start = yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        var end = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();

        return repositoryHandler.findByDateRange(start, end).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
