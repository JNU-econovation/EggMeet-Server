package com.fivepotato.eggmeetserver.provider.security;

import com.fivepotato.eggmeetserver.dto.User.AppTokenDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class AppTokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 2;    // 2일
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 30;  // 30일

    private final Key key;

    @Autowired
    public AppTokenProvider(@Value("${jwt-secret}") final String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AppTokenDto generateAppTokenDto(Authentication authentication) {
        // 권한들 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)        // payload "auth": "ROLE_USER"
                .setExpiration(accessTokenExpiresIn)        // payload "exp": 1516239022 (예시)
                .signWith(key, SignatureAlgorithm.HS512)    // header "alg": "HS512"
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + REFRESH_TOKEN_EXPIRE_TIME))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return AppTokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }
}
