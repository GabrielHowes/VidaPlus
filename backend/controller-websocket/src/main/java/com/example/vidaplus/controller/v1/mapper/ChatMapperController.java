package com.example.vidaplus.controller.v1.mapper;

import com.example.vidaplus.common.config.model.TypingEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChatMapperController {

    @Mapping(target = "roomId", source = "roomId")
    TypingEvent toTypingEvent(TypingEvent event, Long roomId);

}
