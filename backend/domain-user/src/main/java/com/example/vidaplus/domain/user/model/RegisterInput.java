package com.example.vidaplus.domain.user.model;

import java.util.Map;


public record RegisterInput(
        String name,
        String username,
        String email,
        String password,
        Map<String, Object> credentialInfo,
        String role
) {}
