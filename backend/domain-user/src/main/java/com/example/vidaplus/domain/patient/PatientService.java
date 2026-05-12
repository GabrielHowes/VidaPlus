package com.example.vidaplus.domain.patient;

import com.example.vidaplus.domain.patient.model.PatientDto;
import com.example.vidaplus.domain.patient.model.PatientInput;
import com.example.vidaplus.domain.patient.port.PatientApiPort;
import com.example.vidaplus.domain.patient.port.PatientSpiPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class PatientService implements PatientApiPort {

    private final PatientSpiPort patientSpiPort;

    @Override
    public PatientDto create(PatientInput input) {
        return patientSpiPort.create(input);
    }

    @Override
    public List<PatientDto> listAll() {
        return patientSpiPort.listAll();
    }

    @Override
    public PatientDto findByCpf(String cpf) {
        return patientSpiPort.findByCpf(cpf);
    }

    @Override
    public PatientDto update(PatientInput input, String cpf) {
        return patientSpiPort.update(input, cpf);
    }

    @Override
    public String delete(String cpf) {
        return patientSpiPort.delete(cpf);
    }
}
