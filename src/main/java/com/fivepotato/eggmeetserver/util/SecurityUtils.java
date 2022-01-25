package com.fivepotato.eggmeetserver.util;

import com.fivepotato.eggmeetserver.exception.CustomAuthenticationException;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import lombok.NoArgsConstructor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@NoArgsConstructor
public class SecurityUtils {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String UPPER_BEARER_PREFIX = "Bearer ";
    public static final String LOWER_BEARER_PREFIX = "bearer ";

    public static Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new CustomAuthenticationException(ErrorCode.ERROR_SECURITY_CONTEXT);
        }

        return Long.parseLong(authentication.getName());
    }

    // Http Authorization Header 에서 토큰 정보를 꺼내오기
    public static String parseTokenFromHttpHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) &&
                (bearerToken.startsWith(UPPER_BEARER_PREFIX) || bearerToken.startsWith(LOWER_BEARER_PREFIX))) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // WebSocket Authorization Header 에서 토큰 정보를 꺼내오기
    public static String parseTokenFromWebSocketHeader(StompHeaderAccessor accessor) {
        String bearerToken = accessor.getFirstNativeHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) &&
                (bearerToken.startsWith(UPPER_BEARER_PREFIX) || bearerToken.startsWith(LOWER_BEARER_PREFIX))) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
