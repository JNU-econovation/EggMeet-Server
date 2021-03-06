package com.fivepotato.eggmeetserver.domain.user;

import com.fivepotato.eggmeetserver.domain.chat.Chatroom;
import com.fivepotato.eggmeetserver.domain.chat.Message;
import com.fivepotato.eggmeetserver.domain.mentoring.MenteeArea;
import com.fivepotato.eggmeetserver.domain.mentoring.MentorArea;
import com.fivepotato.eggmeetserver.domain.mentoring.Mentoring;
import com.fivepotato.eggmeetserver.dto.user.UserProfileUpdateDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nickname;

    @NotNull
    private int age;

    @NotNull
    private Sex sex;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Location location;

    private String description;

    @NotNull
    private int pictureIndex;

    @NotNull
    private boolean onlineAvailable;

    @NotNull
    private boolean offlineAvailable;

    @OneToOne(mappedBy = "mentor", orphanRemoval = true)
    private MentorArea mentorArea;

    @OneToOne(mappedBy = "mentee", orphanRemoval = true)
    private MenteeArea menteeArea;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Mentoring> mentorMentoring = new ArrayList<>();

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Mentoring>  menteeMentoring = new ArrayList<>();

    @NotNull
    private float mentorRating = 0.0f;

    @NotNull
    private float menteeRating = 0.0f;

    @NotNull
    private int growthPoint = 5;

    @NotNull
    private int growthGrade = 1;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Chatroom> mentorChatroom = new ArrayList<>();

    @OneToMany(mappedBy = "mentee", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Chatroom> menteeChatroom = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    // blocklist, blockedlist?

    @NotNull
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @NotNull
    private String email;

    @NotNull
    private String encodedEmail;

    @NotNull
    private Role role;

    @Builder
    public User(String nickname, int age, Sex sex, Location location, String description, int pictureIndex, boolean onlineAvailable, boolean offlineAvailable, LoginType loginType, String encodedEmail, String email, Role role) {
        this.nickname = nickname;
        this.age = age;
        this.sex = sex;
        this.location = location;
        this.description = description;
        this.pictureIndex = pictureIndex;
        this.onlineAvailable = onlineAvailable;
        this.offlineAvailable = offlineAvailable;
        this.loginType = loginType;
        this.email = email;
        this.encodedEmail = encodedEmail;
        this.role = role;
    }

    public void updateUserProfile(UserProfileUpdateDto userProfileUpdateDto) {
        this.nickname = userProfileUpdateDto.getNickname();
        this.location = userProfileUpdateDto.getLocation();
        this.description = userProfileUpdateDto.getDescription();
        this.pictureIndex = userProfileUpdateDto.getPictureIndex();
        this.onlineAvailable = userProfileUpdateDto.isOnlineAvailable();
        this.offlineAvailable = userProfileUpdateDto.isOfflineAvailable();
    }
}
