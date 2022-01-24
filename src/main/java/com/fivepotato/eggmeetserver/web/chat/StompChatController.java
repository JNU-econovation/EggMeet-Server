package com.fivepotato.eggmeetserver.web.chat;

import com.fivepotato.eggmeetserver.dto.chat.MessageSaveDto;
import com.fivepotato.eggmeetserver.exception.NoContentException;
import com.fivepotato.eggmeetserver.service.chat.ChatroomService;
import com.fivepotato.eggmeetserver.service.chat.MessageService;
import com.fivepotato.eggmeetserver.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final MessageService messageService;
    private final ChatroomService chatroomService;

    // 특정 Broker로 메세지 전달
    private final SimpMessagingTemplate template;

    // StompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    // "/pub/chat/room/enter"
    @MessageMapping("/chat/room/{roomId}/message")
    public void sendMessage(@DestinationVariable Long roomId, MessageSaveDto messageSaveDto) {
        if (!chatroomService.isParticipantByChatroomId(roomId)) {
            throw new IllegalArgumentException();
        }

        Long myId = SecurityUtils.getCurrentUserId();
        messageService.createMessage(roomId, myId, messageSaveDto);

        template.convertAndSend("/sub/chat/room/" + roomId, messageSaveDto);
    }
}
