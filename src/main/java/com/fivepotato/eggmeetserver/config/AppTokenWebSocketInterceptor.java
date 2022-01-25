package com.fivepotato.eggmeetserver.config;

import com.fivepotato.eggmeetserver.provider.security.AppTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("[MESSAGE] : " + message);
//        if (StompCommand.CONNECT.equals(accessor.getCommand()) ||
//                StompCommand.SEND.equals(accessor.getCommand())) {
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String jwt = parseTokenFromWebSocketHeader(accessor);
            appTokenProvider.validateToken(jwt);

            Authentication authentication = appTokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            accessor.setUser(authentication);
        }

        return message;
    }

//    if (StompCommand.CONNECT == cmd || StompCommand.SEND == cmd) {
//        Authentication authenticatedUser = null;
//        String authorization = accessor.getFirstNativeHeader("Authorization:");
//        String credentialsToDecode = authorization.split("\\s")[1];
//        String credentialsDecoded = StringUtils.newStringUtf8(Base64.decodeBase64(credentialsToDecode));
//        String[] credentialsDecodedSplit = credentialsDecoded.split(":");
//        final String username = credentialsDecodedSplit[0];
//        final String password = credentialsDecodedSplit[1];
//        authenticatedUser = userAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        if (authenticatedUser == null) {
//            throw new AccessDeniedException();
//        }
//        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
//        accessor.setUser(authenticatedUser);
//    }
//        return message;
}
