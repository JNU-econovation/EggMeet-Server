package com.fivepotato.eggmeetserver.domain.chat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToMany(mappedBy = "chatrooms", fetch = FetchType.EAGER)
    @JsonBackReference
    private List<User> participants = new ArrayList<>();

    @OneToMany(mappedBy = "chatroom", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JsonManagedReference
    private List<Message> messages = new ArrayList<>();
}
