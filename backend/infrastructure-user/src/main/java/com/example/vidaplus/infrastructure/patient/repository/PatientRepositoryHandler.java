package com.example.vidaplus.infrastructure.patient.repository;

import com.example.vidaplus.infrastructure.patient.PatientRepository;
import com.example.vidaplus.infrastructure.patient.entity.PatientEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PatientRepositoryHandler {

    private final PatientRepository repository;

    public PatientEntity save(PatientEntity entity) {
        return repository.save(entity);
    }

    public PatientEntity findUserByEmail(String email){
        return repository.findUserByEmail(email)
                .orElse(null);
    }

    public List<PatientEntity> findAll() {
        return repository.findAll();
    }

    public Optional<PatientEntity> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<PatientEntity> findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public void delete(PatientEntity entity) {
        repository.delete(entity);
    }

}
