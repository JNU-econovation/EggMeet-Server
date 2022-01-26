package com.fivepotato.eggmeetserver.web.chat;

import com.fivepotato.eggmeetserver.dto.chat.ChatroomInfoDto;
import com.fivepotato.eggmeetserver.dto.chat.ChatroomPreviewDto;
import com.fivepotato.eggmeetserver.dto.chat.ChatroomTempInfoDto;
import com.fivepotato.eggmeetserver.dto.chat.MessageInfoDto;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import com.fivepotato.eggmeetserver.service.chat.ChatroomService;
import com.fivepotato.eggmeetserver.service.chat.MessageService;
import com.fivepotato.eggmeetserver.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatroomService chatRoomService;
    private final MessageService messageService;

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

    @GetMapping("/chat/room")
    public ResponseEntity<List<ChatroomPreviewDto>> getMyChatroomPreviewDtos() {
        return new ResponseEntity<>(
                chatRoomService.getMyChatroomPreviewDtos(),
                HttpStatus.OK
        );
    }

    @GetMapping("/chat/room/{roomId}")
    public ResponseEntity<ChatroomInfoDto> getChatroomInfoDtoByRoomId(@PathVariable Long roomId) {
        return new ResponseEntity<>(
                chatRoomService.getChatroomInfoDtoByRoomId(roomId),
                HttpStatus.OK
        );
    }

    @GetMapping("/chat/room/{roomId}/message/history")
    public ResponseEntity<List<MessageInfoDto>> getMessageInfoDtosHistoryByRoomId(@PathVariable Long roomId) {
        Long myId = SecurityUtils.getCurrentUserId();
        if(!chatRoomService.isParticipantByChatroomId(roomId, myId)) {
            throw new IllegalArgumentException(ErrorCode.NOT_CHATROOM_PARTICIPANT + myId);
        }

        return new ResponseEntity<>(
                messageService.getMessagesByChatroomId(roomId),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/chat/room/{roomId}")
    public ResponseEntity<Void> exitChatroomByRoomId(@PathVariable Long roomId) {
        chatRoomService.deleteChatroomByChatroomId(roomId);

        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }
}
