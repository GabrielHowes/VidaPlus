package com.example.vidaplus.common.config.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;


import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final JwtDecoder jwtDecoder;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor
                .getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) return message;


        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            log.info("[WS] Authorization header recebido: {}", authHeader);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                log.warn("[WS] Tentativa de conexão sem token");
                return null;
            }

            try {
                Jwt jwt = jwtDecoder.decode(authHeader.substring(7));
                JwtAuthenticationToken auth = new JwtAuthenticationToken(
                        jwt,
                        List.of(),
                        jwt.getSubject()
                );

                accessor.setUser(auth);
                log.debug("[WS] Usuário autenticado: {}", jwt.getSubject());
            } catch (Exception e) {
                log.warn("[WS] Token inválido: {}", e.getMessage());
                return null;
            }
        }

        return message;
    }
}