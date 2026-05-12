package com.example.vidaplus.controller.v1.user.request;

import java.util.Map;


public record RegisterRequest(
        String name,
        String username,
        String email,
        String password,
        Map<String, Object> credentialInfo,
        String role
) {
}
