package com.example.vidaplus.domain.appointment.port;

import com.example.vidaplus.domain.appointment.model.AppointmentDetailedDto;
import com.example.vidaplus.domain.appointment.model.AppointmentDto;
import com.example.vidaplus.domain.appointment.model.AppointmentInput;


import java.util.List;


public interface AppointmentApiPort {

    AppointmentDto appointmentAdd(AppointmentInput input, String email);

    List<AppointmentDto> appointmentList();

    List<AppointmentDetailedDto> appointmentDetailedList();

    List<AppointmentDto> appointmentListById(String email);

    AppointmentDto appointmentFind(Long appointmentId);

    AppointmentDto appointmentUpdate(AppointmentInput input, Long appointmentId);

    String appointmentDelete(Long appointmentId);

    List<AppointmentDto> listByMonth(int year, int month);

}
