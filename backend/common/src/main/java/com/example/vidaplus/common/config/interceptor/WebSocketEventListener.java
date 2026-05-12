package com.example.vidaplus.common.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;


@Component
@Slf4j
public class WebSocketEventListener {

    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event) {
        log.info("[WS] Sessão conectada: {}", event.getMessage());
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        log.warn("[WS] Sessão desconectada — status: {}, causa: {}",
                event.getCloseStatus(),
                event.getMessage());
    }

    @EventListener
    public void handleSubscribeEvent(SessionSubscribeEvent event) {
        log.info("[WS] Subscribe: {}", event.getMessage());
    }
}