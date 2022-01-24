package com.fivepotato.eggmeetserver.web.mentoring;

import com.fivepotato.eggmeetserver.service.chat.ChatroomService;
import com.fivepotato.eggmeetserver.service.mentoring.MentoringService;
import com.fivepotato.eggmeetserver.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MentoringApiController {

    private final MentoringService mentoringService;
    private final ChatroomService chatroomService;

    @PostMapping("/mentoring/request")
    public ResponseEntity<Long> sendMentoringRequestAndGetChatroomId(@RequestParam(value = "mentorId") Long mentorId) {
        Long myId = SecurityUtils.getCurrentUserId();
        mentoringService.createMentoring(myId, mentorId);

        Long chatroomId = chatroomService.createChatroom(myId, mentorId).getId();

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