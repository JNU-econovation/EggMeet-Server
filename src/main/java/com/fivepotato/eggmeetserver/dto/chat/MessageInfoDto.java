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
    private MessageType type;
    private Long chatroomId;
    private Long writerId;
    private Integer writerPictureIndex;
    private String writerNickname;
    private String content;
    private Long requestId;
    private Long dateTime;

    public MessageInfoDto(Message message) {
        this.id = message.getId();
        this.type = message.getType();
        this.chatroomId = message.getChatroom().getId();
        if (message.getType().equals(MessageType.MESSAGE)) {
            this.writerId = message.getWriter().getId();
            this.writerPictureIndex = message.getWriter().getPictureIndex();
            this.writerNickname = message.getWriter().getNickname();
        }
        this.requestId = message.getRequestId();
        this.content = message.getContent();
        this.dateTime = message.getCreatedDate().toEpochSecond(ZoneOffset.of("+09:00"));
    }
}
