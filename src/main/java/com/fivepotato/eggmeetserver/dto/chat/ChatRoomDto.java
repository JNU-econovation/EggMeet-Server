package com.fivepotato.eggmeetserver.dto.chat;

import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class ChatRoomDto {

    private String id;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomDto create() {
        ChatRoomDto room = new ChatRoomDto();

        room.id = UUID.randomUUID().toString();
        return room;
    }
}
