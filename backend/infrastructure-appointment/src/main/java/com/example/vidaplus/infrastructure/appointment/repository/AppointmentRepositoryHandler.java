package com.example.vidaplus.infrastructure.appointment.repository;

import com.example.vidaplus.domain.appointment.model.AppointmentInput;
import com.example.vidaplus.infrastructure.patient.entity.PatientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AppointmentRepositoryHandler {

    private final AppointmentRepository repository;
    private final AppointmentMapper mapper;

    public void insertAppointment(AppointmentInput input, PatientEntity user) {
        repository.insertAppointment(
                user.getId(),
                input.doctorId(),
                input.appointmentDate(),
                input.type(),
                input.status(),
                input.notes()
        );
    }

    public List<AppointmentEntity> listByPatientId(Long patientId){
        return repository.findAllByPatientId(patientId);
    }

    public List<AppointmentEntity> listByMedicId(Long doctorId){
        return repository.findAllByDoctorId(doctorId);
    }

    public AppointmentEntity save(AppointmentEntity entity) {
        return repository.save(entity);
    }

    public List<AppointmentEntity> findAll() {
        return repository.findAll();
    }

    public Optional<AppointmentEntity> findById(Long id) {
        return repository.findById(id);
    }

    public void delete(AppointmentEntity entity) {
        repository.delete(entity);
    }

    public List<AppointmentEntity> findByDateRange(Instant start, Instant end) {
        return repository.findByAppointmentDateBetween(start, end);
    }

}
