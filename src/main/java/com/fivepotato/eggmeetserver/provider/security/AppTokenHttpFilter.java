package com.fivepotato.eggmeetserver.provider.security;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fivepotato.eggmeetserver.util.SecurityUtils.parseTokenFromHttpHeader;

@RequiredArgsConstructor
public class AppTokenHttpFilter extends OncePerRequestFilter {

    private final AppTokenProvider appTokenProvider;

    // 실제 필터링 로직은 doFilterInternal 에 들어감
    // JWT 토큰의 인증 정보를 현재 쓰레드의 SecurityContext 에 저장하는 역할 수행
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // Request Header 에서 토큰을 꺼냄
        String jwt = parseTokenFromHttpHeader(request);

        // validateToken 으로 토큰 유효성 검사
        // 정상 토큰이면 SecurityContext 에 Authentication 저장
        if (StringUtils.hasText(jwt)) {
            appTokenProvider.validateToken(jwt);

            Authentication authentication = appTokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
