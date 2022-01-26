package com.fivepotato.eggmeetserver.service.chat;

import com.fivepotato.eggmeetserver.domain.chat.Chatroom;
import com.fivepotato.eggmeetserver.domain.chat.ChatroomQueryRepository;
import com.fivepotato.eggmeetserver.domain.chat.ChatroomRepository;
import com.fivepotato.eggmeetserver.domain.user.User;
import com.fivepotato.eggmeetserver.dto.chat.ChatroomInfoDto;
import com.fivepotato.eggmeetserver.dto.chat.ChatroomTempInfoDto;
import com.fivepotato.eggmeetserver.dto.chat.MessageInfoDto;
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
    private final ChatroomQueryRepository chatroomQueryRepository;
    private final UserService userService;

    @Transactional
    public Chatroom createChatroom(Long myId, Long participantId) {
        Chatroom chatroom = chatroomRepository.save(new Chatroom());

        User me = userService.getUserByUserId(myId);
        User participant = userService.getUserByUserId(participantId);
        me.enterMenteeChatroom(chatroom);
        participant.enterMentorChatroom(chatroom);

        return chatroom;
    }

    public Chatroom getChatroomByRoomId(long roomId) {
        return chatroomRepository.findById(roomId)
                .orElseThrow(() -> new NoContentException(ErrorCode.NO_CHATROOM_BY_ROOMID + roomId));
    }

    public ChatroomTempInfoDto getChatroomInfoDtoByRoomId(Long roomId) {
        Chatroom chatroom = getChatroomByRoomId(roomId);
        return new ChatroomTempInfoDto(chatroom);
    }

    public List<ChatroomInfoDto> getMyChatroomInfoDtos() {
        Long myId = SecurityUtils.getCurrentUserId();

        return chatroomQueryRepository.findAllByParticipantsContainsUserId(myId)
                .stream().map(ChatroomInfoDto::new).collect(Collectors.toList());
    }

    public List<ChatroomTempInfoDto> getAllChatroom() {
        return chatroomRepository.findAll().stream().map(ChatroomTempInfoDto::new).collect(Collectors.toList());
    }

    public boolean isParticipantByChatroomId(long chatroomId, long userId) {
        return chatroomQueryRepository.isParticipantByChatroomId(chatroomId, userId);
    }

    @Transactional
    public void deleteChatroomByChatroomId(long chatroomId) {
        Chatroom chatroom = getChatroomByRoomId(chatroomId);
        chatroom.getMentee().exitMenteeChatroom(chatroom);
        chatroom.getMentor().exitMentorChatroom(chatroom);

        chatroomRepository.delete(chatroom);
    }
}
