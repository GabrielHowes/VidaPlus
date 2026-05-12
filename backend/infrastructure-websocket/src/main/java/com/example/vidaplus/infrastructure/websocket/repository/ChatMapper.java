package com.example.vidaplus.infrastructure.websocket.repository;

import com.example.vidaplus.common.config.model.MessageDto;
import com.example.vidaplus.domain.websocket.model.MessageInput;
import com.example.vidaplus.domain.websocket.model.RoomDto;
import com.example.vidaplus.domain.websocket.model.RoomInput;
import com.example.vidaplus.infrastructure.websocket.entity.MessageEntity;
import com.example.vidaplus.infrastructure.websocket.entity.RoomEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface ChatMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    RoomEntity toRoomEntity(RoomInput input);

    RoomDto toRoomDto(RoomEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "room.id", source = "roomId")
    MessageEntity toMessageEntity(MessageInput input);

    @Mapping(target = "roomId", source = "room.id")
    MessageDto toMessageDto(MessageEntity entity);
}
