package com.example.vidaplus.common.config.subscriber;
import com.example.vidaplus.common.config.model.MessageDto;
import com.example.vidaplus.common.config.model.TypingEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class ChatRedisSubscriber implements MessageListener {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;
    private final StringRedisSerializer stringSerializer = new StringRedisSerializer();

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = stringSerializer.deserialize(message.getChannel());
        String body = stringSerializer.deserialize(message.getBody());

        log.info("[Redis] Canal: {}, Body: {}", channel, body);

        try {
            if (channel != null && channel.startsWith("chat:typing:")) {
                String roomId = channel.replace("chat:typing:", "");
                TypingEvent event = objectMapper.readValue(body, TypingEvent.class);
                messagingTemplate.convertAndSend("/topic/typing/" + roomId, event);

            } else if (channel != null && channel.startsWith("chat:")) {
                String roomId = channel.replace("chat:", "");
                MessageDto msg = objectMapper.readValue(body, MessageDto.class);
                messagingTemplate.convertAndSend("/topic/room/" + roomId, msg);
            }
        } catch (Exception e) {
            log.error("[Redis] Erro ao processar. Canal: {}, Erro: {}", channel, e.getMessage());
        }
    }
}