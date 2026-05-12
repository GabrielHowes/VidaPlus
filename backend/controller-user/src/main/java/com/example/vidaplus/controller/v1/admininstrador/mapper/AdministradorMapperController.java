package com.example.vidaplus.controller.v1.admininstrador.mapper;

import com.example.vidaplus.controller.v1.admininstrador.request.AdministradorRequest;
import com.example.vidaplus.controller.v1.admininstrador.response.AdministradorResponse;
import com.example.vidaplus.domain.administrador.model.AdministradorDto;
import com.example.vidaplus.domain.administrador.model.AdministradorInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdministradorMapperController {

    AdministradorInput toInput(AdministradorRequest request);

    AdministradorResponse toResponse(AdministradorDto dto);
}