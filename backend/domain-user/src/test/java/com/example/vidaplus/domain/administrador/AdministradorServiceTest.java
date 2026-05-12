package com.example.vidaplus.domain.administrador;

import com.example.vidaplus.domain.administrador.model.AdministradorDto;
import com.example.vidaplus.domain.administrador.model.AdministradorInput;
import com.example.vidaplus.domain.administrador.port.AdministradorSpiPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdministradorServiceTest {

    @Mock
    private AdministradorSpiPort spiPort;

    @InjectMocks
    private AdministradorService service;

    @Test
    void shouldCallSpiToCreateAdmin() {
        var input = AdministradorInput.builder().nome("Nome").build();
        var dto = AdministradorDto.builder().nome("Nome").build();

        when(spiPort.cadastroAdmin(input)).thenReturn(dto);

        var result = service.cadastroAdmin(input);

        assertEquals(dto, result);
        verify(spiPort).cadastroAdmin(input);
    }

    @Test
    void shouldCallSpiToListAdmins() {
        var dto = AdministradorDto.builder().nome("Nome").build();

        when(spiPort.listaAdmins()).thenReturn(List.of(dto));

        var result = service.listaAdmins();

        assertEquals(1, result.size());
        assertEquals(dto, result.get(0));
        verify(spiPort).listaAdmins();
    }

    @Test
    void shouldCallSpiToFindAdmin() {
        var dto = AdministradorDto.builder().nome("Nome").build();

        when(spiPort.encontraAdmin("123")).thenReturn(dto);

        var result = service.encontraAdmin("123");

        assertEquals(dto, result);
        verify(spiPort).encontraAdmin("123");
    }

    @Test
    void shouldCallSpiToUpdateAdmin() {
        var input = AdministradorInput.builder().nome("Novo").build();
        var dto = AdministradorDto.builder().nome("Novo").build();

        when(spiPort.atualizarAdmin(input, "123")).thenReturn(dto);

        var result = service.atualizarAdmin(input, "123");

        assertEquals(dto, result);
        verify(spiPort).atualizarAdmin(input, "123");
    }

    @Test
    void shouldCallSpiToDeleteAdmin() {
        when(spiPort.deleteAdmin("123")).thenReturn("Deletado");

        var result = service.deleteAdmin("123");

        assertEquals("Deletado", result);
        verify(spiPort).deleteAdmin("123");
    }
}