package com.fivepotato.eggmeetserver.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PersonalMessageSaveDto {

    private String content;

    @Builder
    public PersonalMessageSaveDto(String content) {
        this.content = content;
    }
}
