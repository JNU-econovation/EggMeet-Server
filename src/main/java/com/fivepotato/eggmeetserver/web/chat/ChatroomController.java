package com.fivepotato.eggmeetserver.web.chat;

import com.fivepotato.eggmeetserver.dto.chat.ChatroomInfoDto;
import com.fivepotato.eggmeetserver.dto.chat.ChatroomTempInfoDto;
import com.fivepotato.eggmeetserver.service.chat.ChatroomService;
import com.fivepotato.eggmeetserver.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatroomController {

    private final ChatroomService chatRoomService;

    @Deprecated
    @GetMapping("/chat/room/list")
    public ResponseEntity<List<ChatroomTempInfoDto>> getRoomList() {
        return new ResponseEntity<>(
                chatRoomService.getAllChatroom(),
                HttpStatus.OK
        );
    }

    @Deprecated
    @PostMapping("/chat/room")
    public ResponseEntity<Void> createChatroom(@RequestParam(value = "participantId") Long participantId) {
        Long myId = SecurityUtils.getCurrentUserId();
        chatRoomService.createChatroom(myId, participantId);

        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @Deprecated
    @GetMapping("/chat/room/{roomId}")
    public ResponseEntity<ChatroomTempInfoDto> getChatroomDtoByRoomId(@PathVariable Long roomId) {
        return new ResponseEntity<>(
                chatRoomService.getChatroomInfoDtoByRoomId(roomId),
                HttpStatus.OK
        );
    }

    @GetMapping("/chat/room")
    public ResponseEntity<List<ChatroomInfoDto>> getMyChatroomInfoDtos() {
        return new ResponseEntity<>(
                chatRoomService.getMyChatroomInfoDtos(),
                HttpStatus.OK
        );
    }
}
