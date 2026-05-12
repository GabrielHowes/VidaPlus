package com.example.vidaplus.infrastructure.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO appointment (patient_id, doctor_id, appointment_date, type, status, notes, created_at)
            VALUES (:patientId, :doctorId, :appointmentDate, :type, :status, :notes, NOW())
            """, nativeQuery = true)
    void insertAppointment(
            @Param("patientId") Long patientId,
            @Param("doctorId") Long doctorId,
            @Param("appointmentDate") LocalDateTime appointmentDate,
            @Param("type") String type,
            @Param("status") String status,
            @Param("notes") String notes
    );

    List<AppointmentEntity> findAllByPatientId(Long patientId);

    List<AppointmentEntity> findAllByDoctorId(Long doctorId);

    List<AppointmentEntity> findByAppointmentDateBetween(Instant startDate, Instant endDate);
}
