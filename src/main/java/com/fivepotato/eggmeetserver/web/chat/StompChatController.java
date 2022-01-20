package com.fivepotato.eggmeetserver.web.chat;

import com.fivepotato.eggmeetserver.dto.chat.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    // 특정 Broker로 메세지 전달
    private final SimpMessagingTemplate template;

    // StompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    // "/pub/chat/room/enter"
    @MessageMapping("/chat/room/enter")
    public void enterChatRoom(ChatMessageDto chatMessageDto) {
        chatMessageDto.setMessage(chatMessageDto.getWriter() + "님이 채팅방에 참여했습니다.");
        template.convertAndSend("/sub/chat/room/" + chatMessageDto.getRoomId(), chatMessageDto);
    }

    @MessageMapping("/chat/room/message")
    public void sendMessage(ChatMessageDto chatMessageDto) {
        template.convertAndSend("/sub/chat/room/" + chatMessageDto.getRoomId(), chatMessageDto);
    }
}
