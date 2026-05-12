package com.example.vidaplus.domain.patient.port;

import com.example.vidaplus.domain.patient.model.PatientDto;
import com.example.vidaplus.domain.patient.model.PatientInput;


import java.util.List;


public interface PatientSpiPort {

    PatientDto create(PatientInput input);

    List<PatientDto> listAll();

    PatientDto findByCpf(String cpf);

    PatientDto update(PatientInput input, String cpf);

    String delete(String cpf);

}
