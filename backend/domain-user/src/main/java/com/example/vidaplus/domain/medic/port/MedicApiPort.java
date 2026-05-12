package com.example.vidaplus.domain.medic.port;

import com.example.vidaplus.domain.medic.model.MedicDto;
import com.example.vidaplus.domain.medic.model.MedicInput;


import java.util.List;


public interface MedicApiPort {

    MedicDto create(MedicInput input);

    List<MedicDto> listAll();

    MedicDto findByCrm(String Crm);

    MedicDto update(MedicInput input, String Crm);

    String delete(String Crm);

}
