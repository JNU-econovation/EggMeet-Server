package com.fivepotato.eggmeetserver.dto.chat;

import com.fivepotato.eggmeetserver.domain.chat.MessageType;
import com.fivepotato.eggmeetserver.domain.chat.SystemMessageContent;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SystemMessageSaveDto {

    private MessageType type;
    private SystemMessageContent content;
    private Long requestId;

    @Builder
    public SystemMessageSaveDto(MessageType type, SystemMessageContent content, Long requestId) {
        this.type = type;
        this.content = content;
        this.requestId = requestId;
    }
}
