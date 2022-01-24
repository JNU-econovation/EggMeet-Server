package com.fivepotato.eggmeetserver.web.mentoring;

import com.fivepotato.eggmeetserver.domain.chat.MessageType;
import com.fivepotato.eggmeetserver.domain.chat.SystemMessageContent;
import com.fivepotato.eggmeetserver.dto.chat.SystemMessageSaveDto;
import com.fivepotato.eggmeetserver.service.chat.ChatroomService;
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
        mentoringService.createMentoring(myId, mentorId);

        Long chatroomId = chatroomService.createChatroom(myId, mentorId).getId();

        stompChatController.sendSystemMessage(chatroomId,
                SystemMessageSaveDto.builder()
                        .type(MessageType.MENTEE_SYSTEM)
                        .content(SystemMessageContent.MENTORING_REQUEST)
                        .build()
        );

        return new ResponseEntity<>(
                chatroomId,
                HttpStatus.OK
        );
    }

//    @GetMapping("/mentoring/request")
//    public ResponseEntity<List<MentoringRequestDto>> getAllMentoringRequestDtos()

//    @PutMapping("/mentoring/request")
//    public ResponseEntity<Long> acceptMentoringRequestAndGetChatroomId(@RequestParam(value = "requestId") Long requestId)

//    @DeleteMapping("/mentoring/request")
//    public ResponseEntity<Void> denyMentoringRequest(@RequestParam(value = "requestId") Long requestId)
}
