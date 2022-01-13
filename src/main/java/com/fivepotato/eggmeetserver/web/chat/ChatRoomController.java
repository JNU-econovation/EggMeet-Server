package com.fivepotato.eggmeetserver.web.chat;

import com.fivepotato.eggmeetserver.domain.chat.ChatRoomRepository;
import com.fivepotato.eggmeetserver.dto.chat.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    @GetMapping("/chat/room/list")
    public ResponseEntity<List<ChatRoomDto>> getRoomList() {
        return new ResponseEntity<>(
                chatRoomRepository.findAllRooms(),
                HttpStatus.OK
        );
    }

    @PostMapping("/chat/room")
    public ResponseEntity<Void> createChatRoom() {
        chatRoomRepository.createChatRoomDto();

        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }

    @GetMapping("/chat/room/{roomId}")
    public ResponseEntity<ChatRoomDto> getChatRoomDtoByRoomId(@PathVariable String roomId) {
        return new ResponseEntity<>(
                chatRoomRepository.findRoomByRoomId(roomId),
                HttpStatus.OK
        );
    }
}
