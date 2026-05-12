package com.example.vidaplus.domain.administrador;

import com.example.vidaplus.domain.administrador.model.AdministradorDto;
import com.example.vidaplus.domain.administrador.model.AdministradorInput;
import com.example.vidaplus.domain.administrador.port.AdministradorApiPort;
import com.example.vidaplus.domain.administrador.port.AdministradorSpiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministradorService implements AdministradorApiPort {

    private final AdministradorSpiPort spiPort;

    @Override
    public AdministradorDto cadastroAdmin(AdministradorInput input) {
        return spiPort.cadastroAdmin(input);
    }

    @Override
    public List<AdministradorDto> listaAdmins() {
        return spiPort.listaAdmins();
    }

    @Override
    public AdministradorDto encontraAdmin(String cpf) {
        return spiPort.encontraAdmin(cpf);
    }

    @Override
    public AdministradorDto atualizarAdmin(AdministradorInput input, String cpf) {
        return spiPort.atualizarAdmin(input, cpf);
    }

    @Override
    public String deleteAdmin(String cpf) {
        return spiPort.deleteAdmin(cpf);
    }
}