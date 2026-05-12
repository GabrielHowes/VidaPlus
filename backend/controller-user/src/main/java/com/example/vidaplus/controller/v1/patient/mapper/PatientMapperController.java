package com.example.vidaplus.controller.v1.patient.mapper;

import com.example.vidaplus.controller.v1.patient.request.PatientRequest;
import com.example.vidaplus.controller.v1.patient.response.PatientResponse;
import com.example.vidaplus.domain.patient.model.PatientDto;
import com.example.vidaplus.domain.patient.model.PatientInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapperController {

    PatientInput toInput(PatientRequest request);
    PatientResponse toResponse(PatientDto dto);

}
