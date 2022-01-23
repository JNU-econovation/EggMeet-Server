package com.fivepotato.eggmeetserver.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChatroom_Id(Long chatroomId);
}
