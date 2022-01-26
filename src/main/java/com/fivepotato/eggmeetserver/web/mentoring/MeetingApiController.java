package com.fivepotato.eggmeetserver.web.mentoring;

import com.fivepotato.eggmeetserver.domain.chat.MessageType;
import com.fivepotato.eggmeetserver.domain.chat.SystemMessageContent;
import com.fivepotato.eggmeetserver.domain.mentoring.Meeting;
import com.fivepotato.eggmeetserver.dto.chat.SystemMessageSaveDto;
import com.fivepotato.eggmeetserver.dto.mentoring.MeetingSaveDto;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
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
public class MeetingApiController {

    private final MeetingService meetingService;
    private final MentoringService mentoringService;
    private final StompChatController stompChatController;

    @PostMapping("/mentoring/{mentoringId}/meeting/request")
    public ResponseEntity<Void> sendMeetingRequest(@PathVariable Long mentoringId, @RequestBody MeetingSaveDto meetingSaveDto) {
        Long myId = SecurityUtils.getCurrentUserId();
        if (!mentoringService.isParticipantByMentoringId(mentoringId, myId)) {
            throw new IllegalArgumentException(ErrorCode.NOT_MENTORING_PARTICIPANT + mentoringId);
        }

        Long meetingId = meetingService.createMeeting(mentoringId, meetingSaveDto);

        long chatroomId = mentoringService.getChatroomIdByMentoringId(mentoringId);
        stompChatController.sendSystemMessage(chatroomId,
                SystemMessageSaveDto.builder()
                        .type(MessageType.MENTEE_SYSTEM)
                        .content(SystemMessageContent.SCHEDULE_REQUEST)
                        .build()
                );
        stompChatController.sendSystemMessage(chatroomId,
                SystemMessageSaveDto.builder()
                        .type(MessageType.MENTOR_SYSTEM)
                        .content(SystemMessageContent.SCHEDULE_REQUEST)
                        .requestId(meetingId)
                        .build()
        );

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
