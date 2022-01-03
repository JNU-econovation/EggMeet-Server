package com.fivepotato.eggmeetserver.util;

import com.fivepotato.eggmeetserver.exception.CustomAuthenticationException;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor
public class SecurityUtils {

    public static Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new CustomAuthenticationException(ErrorCode.ERROR_SECURITY_CONTEXT);
        }

        return Long.parseLong(authentication.getName());
    }
}
