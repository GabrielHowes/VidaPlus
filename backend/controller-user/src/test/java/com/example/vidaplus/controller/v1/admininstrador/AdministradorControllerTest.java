package com.example.vidaplus.controller.v1.admininstrador;

import com.example.vidaplus.controller.v1.admininstrador.mapper.AdministradorMapperController;
import com.example.vidaplus.controller.v1.admininstrador.request.AdministradorRequest;
import com.example.vidaplus.controller.v1.admininstrador.response.AdministradorResponse;
import com.example.vidaplus.domain.administrador.model.AdministradorDto;
import com.example.vidaplus.domain.administrador.model.AdministradorInput;
import com.example.vidaplus.domain.administrador.port.AdministradorApiPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AdministradorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AdministradorApiPort service;

    @Mock
    private AdministradorMapperController mapper;

    @InjectMocks
    private AdministradorController controller;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldCreateAdmin() throws Exception {
        var request = new AdministradorRequest("Nome", "email@test.com", "12345678901", "Cargo");
        var input = AdministradorInput.builder().nome("Nome").email("email@test.com").cpf("12345678901").cargo("Cargo").build();
        var dto = AdministradorDto.builder().id(1L).nome("Nome").email("email@test.com").cpf("12345678901").cargo("Cargo").createdAt(LocalDateTime.now()).build();
        var response = AdministradorResponse.builder().id(1L).nome("Nome").email("email@test.com").cpf("12345678901").cargo("Cargo").build();

        when(mapper.toInput(any(AdministradorRequest.class))).thenReturn(input);
        when(service.cadastroAdmin(any(AdministradorInput.class))).thenReturn(dto);
        when(mapper.toResponse(any(AdministradorDto.class))).thenReturn(response);

        mockMvc.perform(post("/v1/administrador/cadastro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome"));
    }

    @Test
    void shouldListAdmins() throws Exception {
        var dto = AdministradorDto.builder().id(1L).nome("Nome").build();
        var response = AdministradorResponse.builder().id(1L).nome("Nome").build();

        when(service.listaAdmins()).thenReturn(List.of(dto));
        when(mapper.toResponse(any(AdministradorDto.class))).thenReturn(response);

        mockMvc.perform(get("/v1/administrador"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Nome"));
    }

    @Test
    void shouldFindAdmin() throws Exception {
        var dto = AdministradorDto.builder().id(1L).nome("Nome").cpf("123").build();
        var response = AdministradorResponse.builder().id(1L).nome("Nome").cpf("123").build();

        when(service.encontraAdmin("123")).thenReturn(dto);
        when(mapper.toResponse(any(AdministradorDto.class))).thenReturn(response);

        mockMvc.perform(get("/v1/administrador/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Nome"));
    }

    @Test
    void shouldUpdateAdmin() throws Exception {
        var request = new AdministradorRequest("Novo", "email@test.com", "123", "Cargo");
        var input = AdministradorInput.builder().nome("Novo").build();
        var dto = AdministradorDto.builder().id(1L).nome("Novo").build();
        var response = AdministradorResponse.builder().id(1L).nome("Novo").build();

        when(mapper.toInput(any(AdministradorRequest.class))).thenReturn(input);
        when(service.atualizarAdmin(any(AdministradorInput.class), eq("123"))).thenReturn(dto);
        when(mapper.toResponse(any(AdministradorDto.class))).thenReturn(response);

        mockMvc.perform(put("/v1/administrador/123")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Novo"));
    }

    @Test
    void shouldDeleteAdmin() throws Exception {
        when(service.deleteAdmin("123")).thenReturn("Deletado");

        mockMvc.perform(delete("/v1/administrador/123"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deletado"));
    }
}