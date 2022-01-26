package com.fivepotato.eggmeetserver.web.mentoring;

import com.fivepotato.eggmeetserver.domain.chat.Chatroom;
import com.fivepotato.eggmeetserver.domain.chat.MessageType;
import com.fivepotato.eggmeetserver.domain.chat.SystemMessageContent;
import com.fivepotato.eggmeetserver.dto.chat.SystemMessageSaveDto;
import com.fivepotato.eggmeetserver.service.chat.ChatroomService;
import com.fivepotato.eggmeetserver.service.mentoring.MeetingService;
import com.fivepotato.eggmeetserver.service.mentoring.MentoringService;
import com.fivepotato.eggmeetserver.util.SecurityUtils;
import com.fivepotato.eggmeetserver.web.chat.StompChatController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MentoringApiController {

    private final MentoringService mentoringService;
    private final ChatroomService chatroomService;
    private final StompChatController stompChatController;

    @PostMapping("/mentoring/request")
    public ResponseEntity<Long> sendMentoringRequestAndGetChatroomId(@RequestParam(value = "mentorId") Long mentorId) {
        Long myId = SecurityUtils.getCurrentUserId();
        Chatroom chatroom = chatroomService.createChatroom(myId, mentorId);

        mentoringService.createMentoring(myId, mentorId, chatroom);

        stompChatController.sendSystemMessage(chatroom.getId(),
                SystemMessageSaveDto.builder()
                        .type(MessageType.MENTEE_SYSTEM)
                        .content(SystemMessageContent.MENTORING_REQUEST)
                        .build()
        );
        stompChatController.sendSystemMessage(chatroom.getId(),
                SystemMessageSaveDto.builder()
                        .type(MessageType.MENTOR_SYSTEM)
                        .content(SystemMessageContent.MENTORING_REQUEST)
                        .build()
        );

        return new ResponseEntity<>(
                chatroom.getId(),
                HttpStatus.OK
        );
    }

    @PutMapping("/mentoring/request")
    public ResponseEntity<Void> acceptRequestedMentoring(@RequestParam(value = "requestId") Long requestId) {
        mentoringService.acceptRequestedMentoring(requestId);

        long chatroomId = mentoringService.getChatroomIdByMentoringId(requestId);
        stompChatController.sendSystemMessage(chatroomId,
                SystemMessageSaveDto.builder()
                        .type(MessageType.MENTOR_SYSTEM)
                        .content(SystemMessageContent.MENTORING_ACCEPT)
                        .build()
        );
        stompChatController.sendSystemMessage(chatroomId,
                SystemMessageSaveDto.builder()
                        .type(MessageType.MENTEE_SYSTEM)
                        .content(SystemMessageContent.MENTORING_ACCEPT)
                        .build()
        );
        stompChatController.sendSystemMessage(chatroomId,
                SystemMessageSaveDto.builder()
                        .type(MessageType.MENTEE_SYSTEM)
                        .content(SystemMessageContent.SCHEDULE_REQUEST)
                        .build()
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/mentoring/request")
    public ResponseEntity<Void> denyRequestedMentoring(@RequestParam(value = "requestId") Long requestId) {
        mentoringService.denyRequestedMentoring(requestId);

        long chatroomId = mentoringService.getChatroomIdByMentoringId(requestId);
        stompChatController.sendSystemMessage(chatroomId,
                SystemMessageSaveDto.builder()
                        .type(MessageType.MENTOR_SYSTEM)
                        .content(SystemMessageContent.MENTORING_REJECT)
                        .build()
        );
        stompChatController.sendSystemMessage(chatroomId,
                SystemMessageSaveDto.builder()
                        .type(MessageType.MENTEE_SYSTEM)
                        .content(SystemMessageContent.MENTORING_REJECT)
                        .build()
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
