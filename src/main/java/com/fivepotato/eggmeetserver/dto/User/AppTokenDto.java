package com.fivepotato.eggmeetserver.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppTokenDto {

    private String grantType; // bearer
    private String accessToken;
    private Long accessTokenExpiresIn;
    private String refreshToken;
}
