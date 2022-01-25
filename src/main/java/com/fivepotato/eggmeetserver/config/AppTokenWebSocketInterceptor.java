package com.fivepotato.eggmeetserver.config;

import com.fivepotato.eggmeetserver.provider.security.AppTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static com.fivepotato.eggmeetserver.util.SecurityUtils.parseTokenFromWebSocketHeader;

@RequiredArgsConstructor
@Component
@Slf4j
public class AppTokenWebSocketInterceptor implements ChannelInterceptor {

    private final AppTokenProvider appTokenProvider;

    // WebSocket 을 통해 들어온 요청이 처리되기 전에 실
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String jwt = parseTokenFromWebSocketHeader(accessor);
            appTokenProvider.validateToken(jwt);

            Authentication authentication = appTokenProvider.getAuthentication(jwt);
            accessor.setUser(authentication);
        }

        return message;
    }
}
