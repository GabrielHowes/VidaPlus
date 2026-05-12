package com.example.vidaplus.infrastructure.websocket.repository;

import com.example.vidaplus.infrastructure.websocket.entity.MessageEntity;
import com.example.vidaplus.infrastructure.websocket.entity.RoomEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
@RequiredArgsConstructor
public class ChatRepositoryHandler {

    private final RoomRepository roomRepository;
    private final MessageRepository messageRepository;

    public RoomEntity saveRoom(RoomEntity room) {
        return roomRepository.save(room);
    }

    public List<RoomEntity> findAllRooms() {
        return roomRepository.findAll();
    }

    public RoomEntity findRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public MessageEntity saveMessage(MessageEntity message) {
        return messageRepository.save(message);
    }

    public List<MessageEntity> findMessagesByRoomId(Long roomId) {
        return messageRepository.findByRoomIdOrderByCreatedAtAsc(roomId);
    }
}
