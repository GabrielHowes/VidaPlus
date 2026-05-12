package com.example.vidaplus.infrastructure.medic.adapter;

import com.example.vidaplus.domain.medic.model.MedicDto;
import com.example.vidaplus.domain.medic.model.MedicInput;
import com.example.vidaplus.infrastructure.medic.entity.MedicEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface MedicMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    MedicEntity toEntity(MedicInput input);

    MedicDto toDto(MedicEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromInput(MedicInput input, @MappingTarget MedicEntity entity);
}
