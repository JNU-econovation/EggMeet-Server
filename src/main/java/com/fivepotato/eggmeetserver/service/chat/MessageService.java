package com.fivepotato.eggmeetserver.service.chat;

import com.fivepotato.eggmeetserver.domain.chat.Chatroom;
import com.fivepotato.eggmeetserver.domain.chat.Message;
import com.fivepotato.eggmeetserver.domain.chat.MessageRepository;
import com.fivepotato.eggmeetserver.domain.user.User;
import com.fivepotato.eggmeetserver.dto.chat.MessageInfoDto;
import com.fivepotato.eggmeetserver.dto.chat.MessageSaveDto;
import com.fivepotato.eggmeetserver.domain.chat.MessageType;
import com.fivepotato.eggmeetserver.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final ChatroomService chatroomService;
    private final UserService userService;

    public void createMessage(Long chatroomId, Long myId, MessageSaveDto messageSaveDto) {
        Chatroom chatroom = chatroomService.getChatroomByRoomId(chatroomId);
        User me = userService.getUserByUserId(myId);
        Message message = Message.builder()
                .chatroom(chatroom)
                .type(MessageType.MESSAGE)
                .content(messageSaveDto.getMessage())
                .writer(me)
                .build();

        messageRepository.save(message);
    }

    public List<MessageInfoDto> getMessagesByChatroomId(Long chatroomId) {
        return messageRepository.findAllByChatroom_Id(chatroomId)
                .stream().map(MessageInfoDto::new).collect(Collectors.toList());
    }
}
