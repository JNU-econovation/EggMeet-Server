package com.fivepotato.eggmeetserver.config;

import com.fivepotato.eggmeetserver.provider.security.AppTokenFilter;
import com.fivepotato.eggmeetserver.provider.security.AppTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class AppTokenWebSocketInterceptor implements ChannelInterceptor {

    private final AppTokenProvider appTokenProvider;

    // WebSocket 을 통해 들어온 요청이 처리되기 전에 실
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getCommand() == StompCommand.CONNECT) {
            log.info(accessor.getFirstNativeHeader(AppTokenFilter.AUTHORIZATION_HEADER));
            appTokenProvider.validateToken(accessor.getFirstNativeHeader(AppTokenFilter.AUTHORIZATION_HEADER));
        }

        return message;
    }
}
