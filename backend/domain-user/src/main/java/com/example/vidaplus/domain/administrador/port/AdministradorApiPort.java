package com.example.vidaplus.domain.administrador.port;

import com.example.vidaplus.domain.administrador.model.AdministradorDto;
import com.example.vidaplus.domain.administrador.model.AdministradorInput;

import java.util.List;

public interface AdministradorApiPort {

    AdministradorDto cadastroAdmin(AdministradorInput input);

    List<AdministradorDto> listaAdmins();

    AdministradorDto encontraAdmin(String cpf);

    AdministradorDto atualizarAdmin(AdministradorInput input, String cpf);

    String deleteAdmin(String cpf);

}