package com.fivepotato.eggmeetserver.web.chat;

import com.fivepotato.eggmeetserver.dto.chat.ChatroomInfoDto;
import com.fivepotato.eggmeetserver.service.chat.ChatroomService;
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

    @GetMapping("/chat/room/list")
    public ResponseEntity<List<ChatroomInfoDto>> getRoomList() {
        return new ResponseEntity<>(
                chatRoomService.findAllChatroom(),
                HttpStatus.OK
        );
    }

    @PostMapping("/chat/room")
    public ResponseEntity<Void> createChatroom(@RequestParam(value = "participantId") Long participantId) {
        chatRoomService.createChatroom(participantId);

        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @GetMapping("/chat/room/{roomId}")
    public ResponseEntity<ChatroomInfoDto> getChatroomDtoByRoomId(@PathVariable Long roomId) {
        return new ResponseEntity<>(
                chatRoomService.findChatroomByRoomId(roomId),
                HttpStatus.OK
        );
    }
}
