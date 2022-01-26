package com.fivepotato.eggmeetserver.domain.chat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fivepotato.eggmeetserver.domain.mentoring.Mentoring;
import com.fivepotato.eggmeetserver.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@NoArgsConstructor
@Getter
@Entity
public class Chatroom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private User mentee;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private User mentor;

    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonManagedReference
    private List<Message> messages = new ArrayList<>();

    @OneToOne(mappedBy = "chatroom", orphanRemoval = true)
    private Mentoring mentoring;

    public void setMenteeParticipant(User user) {
        this.mentee = user;
    }

    public void setMentorParticipant(User user) {
        this.mentor = user;
    }

    public void clearMenteeParticipant() {
        this.mentee = null;
    }

    public void clearMentorParticipant() {
        this.mentor = null;
    }
}
