package com.example.vidaplus.infrastructure.administrator.repository;

import com.example.vidaplus.infrastructure.administrator.AdministratorRepository;
import com.example.vidaplus.infrastructure.administrator.entity.AdministratorEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdministratorRepositoryHandler {

    private final AdministratorRepository repository;

    public AdministratorEntity save(AdministratorEntity entity){
        return repository.save(entity);
    }

    public List<AdministratorEntity> findAll() {
        return repository.findAll();
    }

    public Optional<AdministratorEntity> findByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    public AdministratorEntity findByEmail(String email){
        return repository.findByEmail(email).orElse(null);
    }

    public void delete(AdministratorEntity entity) {
        repository.delete(entity);
    }

}
