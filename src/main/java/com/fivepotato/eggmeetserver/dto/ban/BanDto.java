package com.fivepotato.eggmeetserver.dto.ban;

import com.fivepotato.eggmeetserver.domain.Ban.Ban;
import com.fivepotato.eggmeetserver.domain.Ban.Reason;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BanDto {

    private String bannedEmail;
    private Reason reason;

    @Builder
    public BanDto(String bannedEmail, Reason reason) {
        this.bannedEmail = bannedEmail;
        this.reason = reason;
    }

    public Ban toEntity() {
        return Ban.builder()
                .bannedEmail(bannedEmail)
                .reason(reason)
                .build();
    }
}
