package com.example.vidaplus.infrastructure.patient.adapter;

import com.example.vidaplus.domain.patient.model.PatientDto;
import com.example.vidaplus.domain.patient.model.PatientInput;
import com.example.vidaplus.infrastructure.patient.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface PatientMapper {

    PatientMapper INSTANCE = Mappers.getMapper(PatientMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    PatientEntity toEntity(PatientInput input);

    PatientDto toDto(PatientEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromInput(PatientInput input, @MappingTarget PatientEntity entity);
}
