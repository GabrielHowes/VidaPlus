package com.example.vidaplus.domain.websocket;

import com.example.vidaplus.common.config.model.MessageDto;
import com.example.vidaplus.domain.websocket.model.MessageInput;
import com.example.vidaplus.domain.websocket.model.RoomDto;
import com.example.vidaplus.domain.websocket.model.RoomInput;
import com.example.vidaplus.domain.websocket.port.ChatApiPort;
import com.example.vidaplus.domain.websocket.port.ChatSpiPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class ChatService implements ChatApiPort {

    private final ChatSpiPort chatSpiPort;

    @Override
    public RoomDto createRoom(RoomInput input) {
        return chatSpiPort.createRoom(input);
    }

    @Override
    public List<RoomDto> listRooms() {
        return chatSpiPort.listRooms();
    }

    @Override
    public MessageDto sendMessage(MessageInput input) {
        return chatSpiPort.saveMessage(input);
    }

    @Override
    public List<MessageDto> listMessages(Long roomId) {
        return chatSpiPort.listMessages(roomId);
    }
}
