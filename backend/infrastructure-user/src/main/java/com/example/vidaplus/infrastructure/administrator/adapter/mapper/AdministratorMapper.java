package com.example.vidaplus.infrastructure.administrator.adapter.mapper;

import com.example.vidaplus.domain.administrador.model.AdministradorDto;
import com.example.vidaplus.domain.administrador.model.AdministradorInput;
import com.example.vidaplus.infrastructure.administrator.entity.AdministratorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AdministratorMapper {

    @Mapping(target = "name", source = "nome")
    @Mapping(target = "jobTitle", source = "cargo")
    @Mapping(target = "cardId", constant = "TEMP")
    AdministratorEntity toEntity(AdministradorInput input);

    @Mapping(target = "nome", source = "name")
    @Mapping(target = "cargo", source = "jobTitle")
    AdministradorDto toDto(AdministratorEntity entity);

}
