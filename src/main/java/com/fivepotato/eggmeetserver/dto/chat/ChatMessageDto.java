package com.fivepotato.eggmeetserver.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {

    private Long roomId;
    private String writer;
    private String message;
}
