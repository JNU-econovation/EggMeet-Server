package com.fivepotato.eggmeetserver.dto.chat;

import com.fivepotato.eggmeetserver.domain.chat.Message;
import com.fivepotato.eggmeetserver.domain.chat.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageInfoDto {

    private Long id;
    private MessageType type;
    private String content;
    private Long writerId;
    private String writerNickname;

    public MessageInfoDto(Message message) {
        this.id = message.getId();
        this.type = message.getType();
        this.content = message.getContent();
        this.writerId = message.getWriter().getId();
        this.writerNickname = message.getWriter().getNickname();
    }
}
