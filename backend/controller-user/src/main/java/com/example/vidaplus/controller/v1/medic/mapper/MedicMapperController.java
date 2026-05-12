package com.example.vidaplus.controller.v1.medic.mapper;

import com.example.vidaplus.controller.v1.medic.request.MedicRequest;
import com.example.vidaplus.controller.v1.medic.response.MedicResponse;
import com.example.vidaplus.domain.medic.model.MedicDto;
import com.example.vidaplus.domain.medic.model.MedicInput;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MedicMapperController {

    MedicInput toInput(MedicRequest request);

    MedicResponse toResponse(MedicDto dto);
}
