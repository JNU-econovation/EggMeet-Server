package com.fivepotato.eggmeetserver.web.chat;

import com.fivepotato.eggmeetserver.dto.chat.MessageSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    // 특정 Broker로 메세지 전달
    private final SimpMessagingTemplate template;

    // StompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    // "/pub/chat/room/enter"
//    @MessageMapping("/chat/room/enter")
//    public void enterChatroom(MessageSaveDto messageSaveDto) {
//        messageSaveDto.setMessage(messageSaveDto.getWriter() + "님이 채팅방에 참여했습니다.");
//        template.convertAndSend("/sub/chat/room/" + messageSaveDto.getRoomId(), messageSaveDto);
//    }
//
//    @MessageMapping("/chat/room/{roomId}/message")
//    public void sendMessage(@DestinationVariable Long roomId, MessageSaveDto messageSaveDto) {
//        template.convertAndSend("/sub/chat/room/" + roomId, messageSaveDto);
//    }
}
