package com.example.vidaplus.infrastructure.websocket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
@Data
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "role", nullable = false)
    private String role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
