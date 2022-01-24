package com.fivepotato.eggmeetserver.dto.chat;

import com.fivepotato.eggmeetserver.domain.chat.Chatroom;
import com.fivepotato.eggmeetserver.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@NoArgsConstructor
@Getter
public class ChatroomTempInfoDto {

    private Long id;
    private List<String> participantNicknames = new ArrayList<>();

    public ChatroomTempInfoDto(Chatroom chatroom) {
        this.id = chatroom.getId();
        for (User participants : chatroom.getParticipants()) {
            participantNicknames.add(participants.getNickname());
        }
    }
}
