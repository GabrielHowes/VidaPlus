package com.example.vidaplus.infrastructure.administrator.adapter;

import com.example.vidaplus.domain.administrador.model.AdministradorDto;
import com.example.vidaplus.domain.administrador.model.AdministradorInput;
import com.example.vidaplus.domain.administrador.port.AdministradorSpiPort;
import com.example.vidaplus.infrastructure.administrator.adapter.mapper.AdministratorMapper;
import com.example.vidaplus.infrastructure.administrator.repository.AdministratorRepositoryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdministratorImplAdapter implements AdministradorSpiPort {

    private final AdministratorRepositoryHandler repositoryHandler;
    private final AdministratorMapper mapper;

    @Override
    public AdministradorDto cadastroAdmin(AdministradorInput input) {
        return mapper.toDto(repositoryHandler.save(mapper.toEntity(input)));
    }

    @Override
    public List<AdministradorDto> listaAdmins() {
        return repositoryHandler.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public AdministradorDto encontraAdmin(String cpf) {
        return repositoryHandler.findByCpf(cpf)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado."));
    }

    @Override
    public AdministradorDto atualizarAdmin(AdministradorInput input, String cpf) {
        var entity = repositoryHandler.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado."));

        entity.setName(input.nome());
        entity.setEmail(input.email());
        entity.setJobTitle(input.cargo());
        entity.setCpf(input.cpf());

        return mapper.toDto(repositoryHandler.save(entity));
    }

    @Override
    public String deleteAdmin(String cpf) {
        var entity = repositoryHandler.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Administrador não encontrado."));
        repositoryHandler.delete(entity);
        return "Administrador deletado com sucesso!";
    }

}
