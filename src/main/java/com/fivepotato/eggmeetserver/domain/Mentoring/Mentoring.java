package com.fivepotato.eggmeetserver.domain.mentoring;

import com.fivepotato.eggmeetserver.domain.chat.Chatroom;
import com.fivepotato.eggmeetserver.domain.user.User;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Mentoring {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private boolean isPending = false;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private User mentor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "mentee_id")
    private User mentee;

    @OneToOne
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;

    @OneToMany(mappedBy = "mentoring", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Meeting> meetings = new ArrayList<>();

    public Mentoring(User mentor, User mentee) {
        this.mentor = mentor;
        this.mentee = mentee;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    public void acceptRequestedMentoring() {
        this.isPending = true;
    }
}
