package com.example.vidaplus.controller.v1.user;

import com.example.vidaplus.controller.v1.user.mapper.UserMapperController;
import com.example.vidaplus.controller.v1.user.request.RegisterRequest;
import com.example.vidaplus.controller.v1.user.request.UserRequest;
import com.example.vidaplus.domain.user.model.UserInput;
import com.example.vidaplus.domain.user.port.UserApiPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints para autenticação de usuários")
public class UserController {

    private final UserApiPort userApiPort;
    private final UserMapperController mapper;

    @Operation(summary = "Realizar login", description = "Autentica o usuário e retorna um token JWT")
    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody UserRequest request
    ){
        return Map.of("Token",userApiPort.login(mapper.toInput(request)).accessToken());
    }

    @Operation(summary = "Registra usuários, médicos e administradores", description = "Registra um novo usuário no sistema")
    @PostMapping("/register")
    public ResponseEntity<Void> register(
            @RequestBody RegisterRequest request
    ) {
        log.info("Request: {}", request);
        userApiPort.register(mapper.toInput(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
