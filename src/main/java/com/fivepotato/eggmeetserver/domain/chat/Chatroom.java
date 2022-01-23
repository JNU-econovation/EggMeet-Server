package com.fivepotato.eggmeetserver.domain.chat;

import com.fivepotato.eggmeetserver.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "chatrooms")
    private List<User> participants = new ArrayList<>();

    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    public void enterParticipant(User user) {
        participants.add(user);
    }

    public void exitParticipant(Long participantId) {
        participants.removeIf(p -> p.getId().equals(participantId));
    }
}
