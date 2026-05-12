package com.example.vidaplus.infrastructure.medic.adapter;

import com.example.vidaplus.domain.medic.model.MedicDto;
import com.example.vidaplus.domain.medic.model.MedicInput;
import com.example.vidaplus.domain.medic.port.MedicSpiPort;
import com.example.vidaplus.infrastructure.medic.entity.MedicEntity;
import com.example.vidaplus.infrastructure.medic.repository.MedicRepositoryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class MedicImplAdapter implements MedicSpiPort {

    private final MedicRepositoryHandler repositoryHandler;
    private final MedicMapper mapper;

    @Override
    public MedicDto create(MedicInput input) {
        MedicEntity entity = mapper.toEntity(input);
        return mapper.toDto(repositoryHandler.save(entity));
    }

    @Override
    public List<MedicDto> listAll() {
        return repositoryHandler.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MedicDto findByCrm(String Crm) {
        MedicEntity entity = repositoryHandler.findByCrm(Crm)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado."));
        return mapper.toDto(entity);
    }

    @Override
    public MedicDto update(MedicInput input, String Crm) {
        MedicEntity entity = repositoryHandler.findByCrm(Crm)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado."));

        mapper.updateEntityFromInput(input, entity);
        return mapper.toDto(repositoryHandler.save(entity));
    }

    @Override
    public String delete(String Crm) {
        MedicEntity entity = repositoryHandler.findByCrm(Crm)
                .orElseThrow(() -> new RuntimeException("Médico não encontrado."));
        repositoryHandler.delete(entity);
        return "Médico deletado com sucesso!";
    }
}
