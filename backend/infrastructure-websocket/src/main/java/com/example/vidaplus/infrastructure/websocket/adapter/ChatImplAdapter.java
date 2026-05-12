package com.example.vidaplus.infrastructure.websocket.adapter;

import com.example.vidaplus.common.config.model.MessageDto;
import com.example.vidaplus.domain.websocket.model.MessageInput;
import com.example.vidaplus.domain.websocket.model.RoomDto;
import com.example.vidaplus.domain.websocket.model.RoomInput;
import com.example.vidaplus.domain.websocket.port.ChatSpiPort;
import com.example.vidaplus.infrastructure.websocket.entity.MessageEntity;
import com.example.vidaplus.infrastructure.websocket.entity.RoomEntity;
import com.example.vidaplus.infrastructure.websocket.repository.ChatMapper;
import com.example.vidaplus.infrastructure.websocket.repository.ChatRepositoryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ChatImplAdapter implements ChatSpiPort {

    private final ChatRepositoryHandler repositoryHandler;
    private final ChatMapper mapper;

    @Override
    public RoomDto createRoom(RoomInput input) {
        RoomEntity entity = mapper.toRoomEntity(input);
        return mapper.toRoomDto(repositoryHandler.saveRoom(entity));
    }

    @Override
    public List<RoomDto> listRooms() {
        return repositoryHandler.findAllRooms().stream()
                .map(mapper::toRoomDto)
                .collect(Collectors.toList());
    }

    @Override
    public MessageDto saveMessage(MessageInput input) {
        MessageEntity entity = mapper.toMessageEntity(input);
        RoomEntity room = repositoryHandler.findRoomById(input.roomId());
        entity.setRole(input.role());
        entity.setRoom(room);

        return mapper.toMessageDto(repositoryHandler.saveMessage(entity));
    }

    @Override
    public List<MessageDto> listMessages(Long roomId) {
        return repositoryHandler.findMessagesByRoomId(roomId).stream()
                .map(mapper::toMessageDto)
                .collect(Collectors.toList());
    }
}
