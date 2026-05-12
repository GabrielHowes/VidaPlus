package com.example.vidaplus.infrastructure.medic.repository;

import com.example.vidaplus.infrastructure.medic.MedicRepository;
import com.example.vidaplus.infrastructure.medic.entity.MedicEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MedicRepositoryHandler {

    private final MedicRepository repository;

    public MedicEntity save(MedicEntity entity) {
        return repository.save(entity);
    }

    public List<MedicEntity> findAll() {
        return repository.findAll();
    }

    public Optional<MedicEntity> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<MedicEntity> findByCrm(String crm) {
        return repository.findByCrm(crm);
    }

    public MedicEntity findByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public void delete(MedicEntity entity) {
        repository.delete(entity);
    }

}
