package com.example.vidaplus.infrastructure.websocket.repository;

import com.example.vidaplus.infrastructure.websocket.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}
