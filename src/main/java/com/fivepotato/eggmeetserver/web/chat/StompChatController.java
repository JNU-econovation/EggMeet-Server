package com.fivepotato.eggmeetserver.web.chat;

import com.fivepotato.eggmeetserver.dto.chat.MessageInfoDto;
import com.fivepotato.eggmeetserver.dto.chat.PersonalMessageSaveDto;
import com.fivepotato.eggmeetserver.dto.chat.SystemMessageSaveDto;
import com.fivepotato.eggmeetserver.service.chat.ChatroomService;
import com.fivepotato.eggmeetserver.service.chat.MessageService;
import com.fivepotato.eggmeetserver.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class StompChatController {

    private final MessageService messageService;
    private final ChatroomService chatroomService;

    // 특정 Broker로 메세지 전달
    private final SimpMessagingTemplate template;

    // StompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    // "/pub/chat/room/~"
    @MessageMapping("/chat/room/{roomId}/message")
    public void sendPersonalMessage(@DestinationVariable Long roomId, @Payload PersonalMessageSaveDto personalMessageSaveDto, Principal principal) {
        log.info("[CONTENT] : " + personalMessageSaveDto.getContent());
        Long myId = Long.parseLong(principal.getName());
//        Long myId = SecurityUtils.getCurrentUserId();
        log.info("[id] : " + myId);
        if (!chatroomService.isParticipantByChatroomId(roomId, myId)) {
            throw new IllegalArgumentException();
        }

        MessageInfoDto messageInfoDto = messageService.createPersonalMessage(roomId, myId, personalMessageSaveDto.getContent());

        template.convertAndSend("/sub/chat/room/" + roomId, messageInfoDto);
    }

//    @MessageMapping("/chat/room/{roomId}/message")
//    public void sendPersonalMessage(@DestinationVariable Long roomId, PersonalMessageSaveDto personalMessageSaveDto) {
//        MessageInfoDto messageInfoDto = messageService.createPersonalMessage(roomId, personalMessageSaveDto);
//
//        template.convertAndSend("/sub/chat/room/" + roomId, messageInfoDto);
//    }

    public void sendSystemMessage(Long roomId, SystemMessageSaveDto systemMessageSaveDto) {
        MessageInfoDto messageInfoDto = messageService.createSystemMessage(roomId, systemMessageSaveDto);

        template.convertAndSend("/sub/chat/room/" + roomId, messageInfoDto);
    }
}
