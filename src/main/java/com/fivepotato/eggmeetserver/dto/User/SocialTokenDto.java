package com.fivepotato.eggmeetserver.dto.user;

import com.fivepotato.eggmeetserver.domain.user.LoginType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SocialTokenDto {

    private LoginType loginType;
    private String socialToken;

    @Builder
    public SocialTokenDto(LoginType loginType, String socialToken) {
        this.loginType = loginType;
        this.socialToken = socialToken;
    }
}
