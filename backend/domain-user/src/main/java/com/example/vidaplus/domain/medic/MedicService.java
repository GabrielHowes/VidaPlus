package com.example.vidaplus.domain.medic;

import com.example.vidaplus.domain.medic.model.MedicDto;
import com.example.vidaplus.domain.medic.model.MedicInput;
import com.example.vidaplus.domain.medic.port.MedicApiPort;
import com.example.vidaplus.domain.medic.port.MedicSpiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class MedicService implements MedicApiPort {

    private final MedicSpiPort medicSpiPort;

    @Override
    public MedicDto create(MedicInput input) {
        return medicSpiPort.create(input);
    }

    @Override
    public List<MedicDto> listAll() {
        return medicSpiPort.listAll();
    }

    @Override
    public MedicDto findByCrm(String Crm) {
        return medicSpiPort.findByCrm(Crm);
    }

    @Override
    public MedicDto update(MedicInput input, String Crm) {
        return medicSpiPort.update(input, Crm);
    }

    @Override
    public String delete(String Crm) {
        return medicSpiPort.delete(Crm);
    }
}
