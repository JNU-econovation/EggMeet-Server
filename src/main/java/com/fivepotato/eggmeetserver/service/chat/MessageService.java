package com.fivepotato.eggmeetserver.service.chat;

import com.fivepotato.eggmeetserver.domain.chat.Chatroom;
import com.fivepotato.eggmeetserver.domain.chat.Message;
import com.fivepotato.eggmeetserver.domain.chat.MessageRepository;
import com.fivepotato.eggmeetserver.domain.user.User;
import com.fivepotato.eggmeetserver.dto.chat.MessageInfoDto;
import com.fivepotato.eggmeetserver.dto.chat.PersonalMessageSaveDto;
import com.fivepotato.eggmeetserver.domain.chat.MessageType;
import com.fivepotato.eggmeetserver.dto.chat.SystemMessageSaveDto;
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

    public MessageInfoDto createPersonalMessage(Long chatroomId, Long writerId, String content) {
        Chatroom chatroom = chatroomService.getChatroomByRoomId(chatroomId);
        User me = userService.getUserByUserId(writerId);
        Message message = messageRepository.save(
                Message.builder()
                        .chatroom(chatroom)
                        .type(MessageType.MESSAGE)
                        .content(content)
                        .writer(me)
                        .build()
        );

        return new MessageInfoDto(message);
    }

//    public MessageInfoDto createPersonalMessage(Long chatroomId, PersonalMessageSaveDto personalMessageSaveDto) {
//        Chatroom chatroom = chatroomService.getChatroomByRoomId(chatroomId);
//        User me = userService.getUserByUserId(writerId);
//        Message message = messageRepository.save(
//                Message.builder()
//                        .chatroom(chatroom)
//                        .type(MessageType.MESSAGE)
//                        .content(personalMessageSaveDto.getContent())
//                        .writer(null)
//                        .build()
//        );
//
//        return new MessageInfoDto(message);
//    }

    public MessageInfoDto createSystemMessage(Long chatroomId, SystemMessageSaveDto systemMessageSaveDto) {
        Chatroom chatroom = chatroomService.getChatroomByRoomId(chatroomId);
        Message message = messageRepository.save(
                Message.builder()
                        .chatroom(chatroom)
                        .type(systemMessageSaveDto.getType())
                        .content(systemMessageSaveDto.getContent().toString())
                        .build()
        );

        return new MessageInfoDto(message);
    }

    public List<MessageInfoDto> getMessagesByChatroomId(Long chatroomId) {
        return messageRepository.findAllByChatroom_Id(chatroomId)
                .stream().map(MessageInfoDto::new).collect(Collectors.toList());
    }
}
