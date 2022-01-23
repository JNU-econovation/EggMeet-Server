package com.fivepotato.eggmeetserver.web.chat;

import com.fivepotato.eggmeetserver.domain.chat.ChatroomRepository;
import com.fivepotato.eggmeetserver.dto.chat.ChatroomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatroomController {

    private final ChatroomRepository chatroomRepository;

    @GetMapping("/chat/room/list")
    public ResponseEntity<List<ChatroomDto>> getRoomList() {
        return new ResponseEntity<>(
                chatroomRepository.findAllRooms(),
                HttpStatus.OK
        );
    }

    @PostMapping("/chat/room")
    public ResponseEntity<Void> createChatroom(@RequestBody ChatroomDto chatroomDto) {
        chatroomRepository.createChatroomDto(chatroomDto);

        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @GetMapping("/chat/room/{roomId}")
    public ResponseEntity<ChatroomDto> getChatroomDtoByRoomId(@PathVariable Long roomId) {
        return new ResponseEntity<>(
                chatroomRepository.findRoomByRoomId(roomId),
                HttpStatus.OK
        );
    }
}
