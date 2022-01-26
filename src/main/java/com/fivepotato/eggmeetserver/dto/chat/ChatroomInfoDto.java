package com.fivepotato.eggmeetserver.dto.chat;

import com.fivepotato.eggmeetserver.domain.chat.Chatroom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ChatroomInfoDto {

    private Long menteeId;
    private Long mentorId;

    public ChatroomInfoDto(Chatroom chatroom) {
        this.menteeId = chatroom.getMentee().getId();
        this.mentorId = chatroom.getMentor().getId();
    }
}
