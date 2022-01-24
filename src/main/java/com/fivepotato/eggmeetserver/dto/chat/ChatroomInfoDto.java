package com.fivepotato.eggmeetserver.dto.chat;

import com.fivepotato.eggmeetserver.domain.chat.Chatroom;
import com.fivepotato.eggmeetserver.domain.user.User;
import com.fivepotato.eggmeetserver.util.SecurityUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@Getter
public class ChatroomInfoDto {

    private Long id;
    private String participantNickname;
    private String recentMessageContent;

    public ChatroomInfoDto(Chatroom chatroom) {
        this.id = chatroom.getId();

        Long myId = SecurityUtils.getCurrentUserId();
        Optional<User> participant = chatroom.getParticipants()
                .stream().filter(p -> !p.getId().equals(myId)).findFirst();
        if (participant.isPresent()) {
            this.participantNickname = participant.get().getNickname();
        } else {
            this.participantNickname = "알 수 없음";
        }

        this.recentMessageContent = chatroom.getMessages().get(chatroom.getMessages().size() - 1).getContent();
    }
}
