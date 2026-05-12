package com.example.vidaplus.infrastructure.appointment.repository;

import com.example.vidaplus.domain.appointment.model.AppointmentDto;
import com.example.vidaplus.domain.appointment.model.AppointmentInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;


import java.util.List;


@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AppointmentEntity toEntity(AppointmentInput input);

    AppointmentDto toDto(AppointmentEntity entity);

    AppointmentDto toDto(AppointmentInput input);

    List<AppointmentDto> toDto(List<AppointmentEntity> entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntityFromInput(AppointmentInput input, @MappingTarget AppointmentEntity entity);
}
