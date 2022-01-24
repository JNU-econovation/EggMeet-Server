package com.fivepotato.eggmeetserver.service.chat;

import com.fivepotato.eggmeetserver.domain.chat.Chatroom;
import com.fivepotato.eggmeetserver.domain.chat.ChatroomRepository;
import com.fivepotato.eggmeetserver.domain.user.User;
import com.fivepotato.eggmeetserver.dto.chat.ChatroomInfoDto;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import com.fivepotato.eggmeetserver.exception.NoContentException;
import com.fivepotato.eggmeetserver.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final UserService userService;

    @Transactional
    public Chatroom createChatroom(Long myId, Long participantId) {
        Chatroom chatroom = chatroomRepository.save(new Chatroom());

        User me = userService.getUserByUserId(myId);
        User participant = userService.getUserByUserId(participantId);
        me.enterChatroom(chatroom);
        participant.enterChatroom(chatroom);

        return chatroom;
    }

    public List<ChatroomInfoDto> findAllChatroom() {
        return chatroomRepository.findAll().stream().map(ChatroomInfoDto::new).collect(Collectors.toList());
    }

    public Chatroom findChatroomByRoomId(long roomId) {
        return chatroomRepository.findById(roomId)
                .orElseThrow(() -> new NoContentException(ErrorCode.NO_CHATROOM_BY_ROOMID + roomId));
    }

    public ChatroomInfoDto findChatroomInfoDtoByRoomId(Long roomId) {
        Chatroom chatroom = findChatroomByRoomId(roomId);
        return new ChatroomInfoDto(chatroom);
    }

    @Transactional
    public void deleteChatroomByChatroomId(long chatroomId) {
        Chatroom chatroom = findChatroomByRoomId(chatroomId);
        for (User participant : chatroom.getParticipants()) {
            participant.exitChatroom(chatroom);
        }

        chatroomRepository.delete(chatroom);
    }
}
