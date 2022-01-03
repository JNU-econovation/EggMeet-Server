package com.fivepotato.eggmeetserver.dto.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AppTokenReissueDto {

    private String accessToken;
    private String refreshToken;
}