package com.example.vidaplus.domain.websocket.port;

import com.example.vidaplus.common.config.model.MessageDto;
import com.example.vidaplus.domain.websocket.model.MessageInput;
import com.example.vidaplus.domain.websocket.model.RoomDto;
import com.example.vidaplus.domain.websocket.model.RoomInput;


import java.util.List;


public interface ChatSpiPort {

    RoomDto createRoom(RoomInput input);

    List<RoomDto> listRooms();

    MessageDto saveMessage(MessageInput input);

    List<MessageDto> listMessages(Long roomId);

}
