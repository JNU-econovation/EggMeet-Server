package com.fivepotato.eggmeetserver.domain.chat;

import com.fivepotato.eggmeetserver.dto.chat.ChatroomDto;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatroomRepository {

    private Map<Long, ChatroomDto> chatroomDtoMap;

    @PostConstruct
    private void init() {
        chatroomDtoMap = new LinkedHashMap<>();
    }

    public List<ChatroomDto> findAllRooms() {
        List<ChatroomDto> chatroomDtos = new ArrayList<>(chatroomDtoMap.values());
        Collections.reverse(chatroomDtos);

        return chatroomDtos;
    }

    public ChatroomDto findRoomByRoomId(Long roomId) {
        return chatroomDtoMap.get(roomId);
    }

    public void createChatroomDto(ChatroomDto chatroomDto) {
        chatroomDtoMap.put(chatroomDto.getId(), chatroomDto);
    }
}
