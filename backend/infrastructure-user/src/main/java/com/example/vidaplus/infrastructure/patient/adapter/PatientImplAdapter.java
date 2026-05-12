package com.example.vidaplus.infrastructure.patient.adapter;

import com.example.vidaplus.domain.patient.model.PatientDto;
import com.example.vidaplus.domain.patient.model.PatientInput;
import com.example.vidaplus.domain.patient.port.PatientSpiPort;
import com.example.vidaplus.infrastructure.patient.entity.PatientEntity;
import com.example.vidaplus.infrastructure.patient.repository.PatientRepositoryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class PatientImplAdapter implements PatientSpiPort {

    private final PatientRepositoryHandler repositoryHandler;
    private final PatientMapper mapper;

    @Override
    public PatientDto create(PatientInput input) {
        PatientEntity entity = mapper.toEntity(input);
        return mapper.toDto(repositoryHandler.save(entity));
    }

    @Override
    public List<PatientDto> listAll() {
        return repositoryHandler.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDto findByCpf(String cpf) {
        PatientEntity entity = repositoryHandler.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));
        return mapper.toDto(entity);
    }

    @Override
    public PatientDto update(PatientInput input, String cpf) {
        PatientEntity entity = repositoryHandler.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));

        mapper.updateEntityFromInput(input, entity);
        return mapper.toDto(repositoryHandler.save(entity));
    }

    @Override
    public String delete(String cpf) {
        PatientEntity entity = repositoryHandler.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado."));
        repositoryHandler.delete(entity);
        return "Paciente deletado com sucesso!";
    }
}
