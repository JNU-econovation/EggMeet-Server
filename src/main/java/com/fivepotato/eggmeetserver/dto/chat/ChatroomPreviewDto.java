package com.fivepotato.eggmeetserver.dto.chat;

import com.fivepotato.eggmeetserver.domain.chat.Chatroom;
import com.fivepotato.eggmeetserver.domain.user.User;
import com.fivepotato.eggmeetserver.util.SecurityUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Getter
public class ChatroomPreviewDto {

    private Long id;
    private String participantNickname;
    private String recentMessageContent;

    public ChatroomPreviewDto(Chatroom chatroom) {
        this.id = chatroom.getId();

        Long myId = SecurityUtils.getCurrentUserId();
        if (chatroom.getMentee() == null && chatroom.getMentor() == null) {
            this.participantNickname = "알 수 없음";
        } else {
            if (Objects.equals(chatroom.getMentee().getId(), myId)) {
                this.participantNickname = chatroom.getMentor().getNickname();
            } else {
                this.participantNickname = chatroom.getMentee().getNickname();
            }
        }

        this.recentMessageContent = chatroom.getMessages().get(chatroom.getMessages().size() - 1).getContent();
    }
}
