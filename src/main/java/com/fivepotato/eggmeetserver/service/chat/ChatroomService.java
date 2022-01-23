package com.fivepotato.eggmeetserver.service.chat;

import com.fivepotato.eggmeetserver.domain.chat.Chatroom;
import com.fivepotato.eggmeetserver.domain.chat.ChatroomRepository;
import com.fivepotato.eggmeetserver.domain.user.User;
import com.fivepotato.eggmeetserver.dto.chat.ChatroomInfoDto;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import com.fivepotato.eggmeetserver.exception.NoContentException;
import com.fivepotato.eggmeetserver.service.user.UserService;
import com.fivepotato.eggmeetserver.util.SecurityUtils;
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
    public void createChatroom(Long participantId) {
        Chatroom chatroom = chatroomRepository.save(new Chatroom());

        User me = userService.getUserByUserId(SecurityUtils.getCurrentUserId());
        User participant = userService.getUserByUserId(participantId);
        chatroom.enterParticipant(me);
        chatroom.enterParticipant(participant);
    }

    public List<ChatroomInfoDto> findAllChatroom() {
        return chatroomRepository.findAll().stream().map(ChatroomInfoDto::new).collect(Collectors.toList());
    }

    public ChatroomInfoDto findChatroomByRoomId(Long roomId) {
        return chatroomRepository.findById(roomId).map(ChatroomInfoDto::new)
                .orElseThrow(() -> new NoContentException(ErrorCode.NO_CHATROOM_BY_ROOMID + roomId));
    }
}
