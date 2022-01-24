package com.fivepotato.eggmeetserver.domain.chat;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
}
