package com.example.vidaplus.infrastructure.appointment.adapter;

import com.example.vidaplus.domain.appointment.model.AppointmentDto;
import com.example.vidaplus.domain.appointment.model.AppointmentInput;
import com.example.vidaplus.infrastructure.appointment.repository.AppointmentEntity;
import com.example.vidaplus.infrastructure.appointment.repository.AppointmentMapper;
import com.example.vidaplus.infrastructure.appointment.repository.AppointmentRepositoryHandler;
import com.example.vidaplus.infrastructure.patient.entity.PatientEntity;
import com.example.vidaplus.infrastructure.patient.repository.PatientRepositoryHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AppointmentImplAdapterTest {

    @Mock
    private AppointmentRepositoryHandler repositoryHandler;

    @Mock
    private PatientRepositoryHandler patientRepositoryHandler;

    @Mock
    private AppointmentMapper mapper;

    @InjectMocks
    private AppointmentImplAdapter adapter;

    @Test
    @DisplayName("Should add appointment successfully")
    void shouldAddAppointmentSuccessfully() {
        // Arrange
        String email = "patient@example.com";
        AppointmentInput input = AppointmentInput.builder()
                .doctorId(1L)
                .appointmentDate(LocalDateTime.now())
                .type("Routine")
                .status("Scheduled")
                .notes("Regular checkup")
                .build();

        PatientEntity patient = PatientEntity.builder().id(100L).email(email).build();

        given(patientRepositoryHandler.findUserByEmail(email)).willReturn(patient);

        // Act
        AppointmentDto result = adapter.appointmentAdd(input, email);

        // Assert
        then(patientRepositoryHandler).should().findUserByEmail(email);
        then(repositoryHandler).should().insertAppointment(eq(input), eq(patient));
    }

    @Test
    @DisplayName("Should list all appointments")
    void shouldListAllAppointments() {
        // Arrange
        AppointmentEntity entity = AppointmentEntity.builder().id(1L).build();
        AppointmentDto dto = AppointmentDto.builder().id(1L).build();

        given(repositoryHandler.findAll()).willReturn(List.of(entity));
        given(mapper.toDto(entity)).willReturn(dto);

        // Act
        List<AppointmentDto> result = adapter.appointmentList();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).id());
        then(repositoryHandler).should().findAll();
        then(mapper).should().toDto(entity);
    }

    @Test
    @DisplayName("Should find appointment by id successfully")
    void shouldFindAppointmentByIdSuccessfully() {
        // Arrange
        Long appointmentId = 1L;
        AppointmentEntity entity = AppointmentEntity.builder().id(appointmentId).build();
        AppointmentDto dto = AppointmentDto.builder().id(appointmentId).build();

        given(repositoryHandler.findById(appointmentId)).willReturn(Optional.of(entity));
        given(mapper.toDto(entity)).willReturn(dto);

        // Act
        AppointmentDto result = adapter.appointmentFind(appointmentId);

        // Assert
        assertNotNull(result);
        assertEquals(appointmentId, result.id());
        then(repositoryHandler).should().findById(appointmentId);
        then(mapper).should().toDto(entity);
    }

    @Test
    @DisplayName("Should throw exception when appointment not found during find")
    void shouldThrowExceptionWhenAppointmentNotFoundDuringFind() {
        // Arrange
        Long appointmentId = 1L;
        given(repositoryHandler.findById(appointmentId)).willReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> adapter.appointmentFind(appointmentId));
        assertEquals("Appointment not found.", exception.getMessage());
        then(repositoryHandler).should().findById(appointmentId);
        then(mapper).shouldHaveNoInteractions();
    }

    @Test
    @DisplayName("Should update appointment successfully")
    void shouldUpdateAppointmentSuccessfully() {
        // Arrange
        Long appointmentId = 1L;
        AppointmentInput input = AppointmentInput.builder().status("Updated").build();
        AppointmentEntity existingEntity = AppointmentEntity.builder().id(appointmentId).status("Scheduled").build();
        AppointmentEntity updatedEntity = AppointmentEntity.builder().id(appointmentId).status("Updated").build();
        AppointmentDto expectedDto = AppointmentDto.builder().id(appointmentId).status("Updated").build();

        given(repositoryHandler.findById(appointmentId)).willReturn(Optional.of(existingEntity));
        given(repositoryHandler.save(existingEntity)).willReturn(updatedEntity);
        given(mapper.toDto(updatedEntity)).willReturn(expectedDto);

        // Act
        AppointmentDto result = adapter.appointmentUpdate(input, appointmentId);

        // Assert
        assertNotNull(result);
        assertEquals("Updated", result.status());
        then(repositoryHandler).should().findById(appointmentId);
        then(mapper).should().updateEntityFromInput(input, existingEntity);
        then(repositoryHandler).should().save(existingEntity);
        then(mapper).should().toDto(updatedEntity);
    }

    @Test
    @DisplayName("Should throw exception when appointment not found during update")
    void shouldThrowExceptionWhenAppointmentNotFoundDuringUpdate() {
        // Arrange
        Long appointmentId = 1L;
        AppointmentInput input = AppointmentInput.builder().build();
        given(repositoryHandler.findById(appointmentId)).willReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> adapter.appointmentUpdate(input, appointmentId));
        assertEquals("Appointment not found.", exception.getMessage());
        then(repositoryHandler).should().findById(appointmentId);
        then(repositoryHandler).should(never()).save(any());
    }

    @Test
    @DisplayName("Should delete appointment successfully")
    void shouldDeleteAppointmentSuccessfully() {
        // Arrange
        Long appointmentId = 1L;
        AppointmentEntity entity = AppointmentEntity.builder().id(appointmentId).build();
        given(repositoryHandler.findById(appointmentId)).willReturn(Optional.of(entity));

        // Act
        String result = adapter.appointmentDelete(appointmentId);

        // Assert
        assertEquals("Appointment deleted successfully!", result);
        then(repositoryHandler).should().findById(appointmentId);
        then(repositoryHandler).should().delete(entity);
    }

    @Test
    @DisplayName("Should throw exception when appointment not found during delete")
    void shouldThrowExceptionWhenAppointmentNotFoundDuringDelete() {
        // Arrange
        Long appointmentId = 1L;
        given(repositoryHandler.findById(appointmentId)).willReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> adapter.appointmentDelete(appointmentId));
        assertEquals("Appointment not found.", exception.getMessage());
        then(repositoryHandler).should().findById(appointmentId);
        then(repositoryHandler).should(never()).delete(any());
    }

    @Test
    @DisplayName("Should list appointments by month")
    void shouldListAppointmentsByMonth() {
        // Arrange
        int year = 2024;
        int month = 3;
        YearMonth yearMonth = YearMonth.of(year, month);
        Instant start = yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant end = yearMonth.atEndOfMonth().atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant();

        AppointmentEntity entity = AppointmentEntity.builder().id(1L).build();
        AppointmentDto dto = AppointmentDto.builder().id(1L).build();

        given(repositoryHandler.findByDateRange(any(Instant.class), any(Instant.class))).willReturn(List.of(entity));
        given(mapper.toDto(entity)).willReturn(dto);

        // Act
        List<AppointmentDto> result = adapter.listByMonth(year, month);

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        then(repositoryHandler).should().findByDateRange(argThat(actualStart ->
                Math.abs(actualStart.toEpochMilli() - start.toEpochMilli()) < 1000),
                argThat(actualEnd -> Math.abs(actualEnd.toEpochMilli() - end.toEpochMilli()) < 1000));
        then(mapper).should().toDto(entity);
    }
}
