package com.example.vidaplus.domain.appointment;

import com.example.vidaplus.domain.appointment.model.AppointmentDetailedDto;
import com.example.vidaplus.domain.appointment.model.AppointmentDto;
import com.example.vidaplus.domain.appointment.model.AppointmentInput;
import com.example.vidaplus.domain.appointment.port.AppointmentApiPort;
import com.example.vidaplus.domain.appointment.port.AppointmentSpiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class AppointmentService implements AppointmentApiPort {

    private final AppointmentSpiPort appointmentSpiPort;

    @Override
    public AppointmentDto appointmentAdd(AppointmentInput input, String email) {
        return appointmentSpiPort.appointmentAdd(input, email);
    }

    @Override
    public List<AppointmentDto> appointmentList() {
        return appointmentSpiPort.appointmentList();
    }

    @Override
    public List<AppointmentDetailedDto> appointmentDetailedList() {
        return appointmentSpiPort.appointmentDetailedList();
    }

    @Override
    public List<AppointmentDto> appointmentListById(String email) {
        return appointmentSpiPort.appointmentListById(email);
    }

    @Override
    public AppointmentDto appointmentFind(Long appointmentId) {
        return appointmentSpiPort.appointmentFind(appointmentId);
    }

    @Override
    public AppointmentDto appointmentUpdate(AppointmentInput input, Long appointmentId) {
        return appointmentSpiPort.appointmentUpdate(input, appointmentId);
    }

    @Override
    public String appointmentDelete(Long appointmentId) {
        return appointmentSpiPort.appointmentDelete(appointmentId);
    }

    @Override
    public List<AppointmentDto> listByMonth(int year, int month) {
        return appointmentSpiPort.listByMonth(year, month);
    }
}
