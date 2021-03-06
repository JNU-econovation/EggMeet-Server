package com.fivepotato.eggmeetserver.domain.chat;

import com.fivepotato.eggmeetserver.domain.BaseTimeEntity;
import com.fivepotato.eggmeetserver.domain.user.User;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Message extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    @NotNull
    private Chatroom chatroom;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MessageType type;

    @NotNull
    private String content;

    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    @Builder
    public Message(Chatroom chatroom, MessageType type, String content, Long requestId, User writer) {
        this.chatroom = chatroom;
        this.type = type;
        this.content = content;
        this.requestId = requestId;
        this.writer = writer;
    }
}