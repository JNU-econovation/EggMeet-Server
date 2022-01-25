package com.fivepotato.eggmeetserver.dto.chat;

import com.fivepotato.eggmeetserver.domain.chat.Message;
import com.fivepotato.eggmeetserver.domain.chat.MessageType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZoneOffset;

@Getter
@NoArgsConstructor
public class MessageInfoDto {

    private Long id;
    private Long chatroomId;
    private Long writerId;
    private String writerNickname;
    private MessageType type;
    private String content;
    private Long dateTime;

    public MessageInfoDto(Message message) {
        this.id = message.getId();
        this.chatroomId = message.getChatroom().getId();
//        this.writerId = message.getWriter().getId();
//        this.writerNickname = message.getWriter().getNickname();
        this.type = message.getType();
        this.content = message.getContent();
        this.dateTime = message.getCreatedDate().toEpochSecond(ZoneOffset.of("+09:00"));
    }
}
