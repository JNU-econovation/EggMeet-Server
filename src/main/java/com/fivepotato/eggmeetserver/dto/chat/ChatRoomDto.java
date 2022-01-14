package com.fivepotato.eggmeetserver.dto.chat;

import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class ChatRoomDto {

    private Long id;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomDto create() {
        ChatRoomDto room = new ChatRoomDto();

        // TODO: Write the logic to init room id
        room.id = 1L;
        return room;
    }
}
