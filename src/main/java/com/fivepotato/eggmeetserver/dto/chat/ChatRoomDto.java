package com.fivepotato.eggmeetserver.dto.chat;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class ChatRoomDto {

    private Long id;

    @Builder
    public ChatRoomDto(Long id) {
        this.id = id;
    }
}
