package com.example.vidaplus.infrastructure.patient;

import com.example.vidaplus.infrastructure.patient.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {

    Optional<PatientEntity> findByCpf(String cpf);

    Optional<PatientEntity> findUserByEmail(String email);

}
