package com.fivepotato.eggmeetserver.dto.ban;

import com.fivepotato.eggmeetserver.domain.ban.Ban;
import com.fivepotato.eggmeetserver.domain.ban.Reason;
import com.fivepotato.eggmeetserver.domain.user.LoginType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BanDto {

    private LoginType bannedLoginType;
    private String bannedEmail;
    private Reason reason;

    @Builder
    public BanDto(LoginType bannedLoginType, String bannedEmail, Reason reason) {
        this.bannedLoginType = bannedLoginType;
        this.bannedEmail = bannedEmail;
        this.reason = reason;
    }

    public Ban toEntity() {
        return Ban.builder()
                .bannedLoginType(bannedLoginType)
                .bannedEmail(bannedEmail)
                .reason(reason)
                .build();
    }
}
