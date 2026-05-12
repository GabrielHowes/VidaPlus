package com.example.vidaplus.controller.v1;

import com.example.vidaplus.common.config.model.MessageDto;
import com.example.vidaplus.domain.websocket.model.RoomDto;
import com.example.vidaplus.domain.websocket.model.RoomInput;
import com.example.vidaplus.domain.websocket.port.ChatApiPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/v1/chat")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "Gerenciamento de salas e histórico de mensagens")
public class ChatController {

    private final ChatApiPort chatApiPort;

    @Operation(summary = "Criar sala", description = "Cria uma nova sala de chat")
    @PostMapping("/room")
    public RoomDto createRoom(@RequestBody RoomInput input) {
        return chatApiPort.createRoom(input);
    }

    @Operation(summary = "Listar salas", description = "Lista todas as salas de chat disponíveis")
    @GetMapping("/room")
    public List<RoomDto> listRooms() {
        return chatApiPort.listRooms();
    }

    @Operation(summary = "Histórico de mensagens", description = "Lista o histórico de mensagens de uma sala")
    @GetMapping("/message/{roomId}")
    public List<MessageDto> listMessages(@PathVariable Long roomId) {
        return chatApiPort.listMessages(roomId);
    }
}