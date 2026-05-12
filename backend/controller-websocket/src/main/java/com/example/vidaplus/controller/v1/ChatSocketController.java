package com.example.vidaplus.controller.v1;

import com.example.vidaplus.common.config.model.MessageDto;
import com.example.vidaplus.controller.v1.mapper.ChatMapperController;
import com.example.vidaplus.domain.websocket.model.MessageInput;
import com.example.vidaplus.common.config.model.TypingEvent;
import com.example.vidaplus.domain.websocket.port.ChatApiPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatSocketController {

    private final ChatApiPort chatApiPort;
    private final RedisTemplate<String, Object> redisTemplate;
    private final ChatMapperController mapper;


    @MessageMapping("/chat/{roomId}/sendMessage")
    public void sendMessage(
            @DestinationVariable Long roomId,
            @Payload MessageInput input) {

        MessageInput inputWithRoom = new MessageInput(input.content(), input.username(), input.role(),roomId);
        MessageDto saved = chatApiPort.sendMessage(inputWithRoom);

        log.info("[Redis] Publicando no canal: chat:{}", roomId);
        redisTemplate.convertAndSend("chat:" + roomId, saved);
        log.info("[Redis] Publicado com sucesso");
    }

    @MessageMapping("/chat/{roomId}/typing")
    public void typing(
            @DestinationVariable Long roomId,
            @Payload TypingEvent event) {

        redisTemplate.convertAndSend("chat:typing:" + roomId, mapper.toTypingEvent(event, roomId));
    }
}
