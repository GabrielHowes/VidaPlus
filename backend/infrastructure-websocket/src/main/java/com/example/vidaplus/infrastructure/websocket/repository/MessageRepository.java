package com.example.vidaplus.infrastructure.websocket.repository;

import com.example.vidaplus.infrastructure.websocket.entity.MessageEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Long> {
    List<MessageEntity> findByRoomIdOrderByCreatedAtAsc(Long roomId);

    List<MessageEntity> findByContentAndUsername(String content, String username, Pageable pageable);
}
