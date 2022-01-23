package com.fivepotato.eggmeetserver.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MessageSaveDto {

    private String message;

    @Builder
    public MessageSaveDto(String message) {
        this.message = message;
    }
}
