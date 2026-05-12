package com.example.vidaplus.controller.v1.mapper;

import com.example.vidaplus.controller.v1.request.AppointmentRequest;
import com.example.vidaplus.controller.v1.response.AppointmentDetailedResponse;
import com.example.vidaplus.controller.v1.response.AppointmentResponse;
import com.example.vidaplus.domain.appointment.model.AppointmentDetailedDto;
import com.example.vidaplus.domain.appointment.model.AppointmentDto;
import com.example.vidaplus.domain.appointment.model.AppointmentInput;
import org.mapstruct.Mapper;


import java.util.List;


@Mapper(componentModel = "spring")
public interface AppointmentMapperContract {

    AppointmentResponse toDomain(AppointmentDto dto);

    List<AppointmentResponse> toDomain(List<AppointmentDto> dto);

    AppointmentDetailedResponse toDetailed(AppointmentDetailedDto dto);

    List<AppointmentDetailedResponse> toDetailed(List<AppointmentDetailedDto> dto);

    AppointmentInput toDomain(AppointmentRequest request);

}
