package com.fivepotato.eggmeetserver.domain.chat;

import com.fivepotato.eggmeetserver.dto.chat.ChatRoomDto;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {

    private Map<Long, ChatRoomDto> chatRoomDtoMap;

    @PostConstruct
    private void init() {
        chatRoomDtoMap = new LinkedHashMap<>();
    }

    public List<ChatRoomDto> findAllRooms() {
        List<ChatRoomDto> chatRoomDtos = new ArrayList<>(chatRoomDtoMap.values());
        Collections.reverse(chatRoomDtos);

        return chatRoomDtos;
    }

    public ChatRoomDto findRoomByRoomId(String roomId) {
        return chatRoomDtoMap.get(roomId);
    }

    public ChatRoomDto createChatRoomDto() {
        ChatRoomDto room = ChatRoomDto.create();
        chatRoomDtoMap.put(room.getId(), room);

        return room;
    }
}
