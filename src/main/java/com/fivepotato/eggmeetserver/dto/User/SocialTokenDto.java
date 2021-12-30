package com.fivepotato.eggmeetserver.dto.User;

import com.fivepotato.eggmeetserver.domain.User.LoginType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class SocialTokenDto {

    private LoginType loginType;
    private String socialToken;

    @Builder
    public SocialTokenDto(LoginType loginType, String socialToken) {
        this.loginType = loginType;
        this.socialToken = socialToken;
    }
}
