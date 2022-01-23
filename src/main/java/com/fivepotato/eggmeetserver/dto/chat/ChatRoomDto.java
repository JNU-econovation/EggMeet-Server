package com.fivepotato.eggmeetserver.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class ChatroomDto {

    private Long id;

    @Builder
    public ChatroomDto(Long id) {
        this.id = id;
    }
}
