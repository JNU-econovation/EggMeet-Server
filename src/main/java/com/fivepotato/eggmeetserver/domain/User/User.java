package com.fivepotato.eggmeetserver.domain.user;

import com.fivepotato.eggmeetserver.domain.mentoring.MenteeArea;
import com.fivepotato.eggmeetserver.domain.mentoring.MentorArea;
import com.fivepotato.eggmeetserver.dto.user.UserProfileUpdateDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private boolean isOnlineAvailable;

    @NotNull
    private boolean isOfflineAvailable;

    @OneToOne(mappedBy = "mentor", orphanRemoval = true)
    private MentorArea mentorArea;

    @OneToOne(mappedBy = "mentee", orphanRemoval = true)
    private MenteeArea menteeArea;

    @NotNull
    private float mentorRating = 0.0f;

    @NotNull
    private float menteeRating = 0.0f;

    @NotNull
    private int growthPoint = 5;

    @NotNull
    private int growthGrade = 1;

    // chats, messages, blocklist

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
    public User(String nickname, int age, Sex sex, Location location, String description, int pictureIndex, boolean isOnlineAvailable, boolean isOfflineAvailable, LoginType loginType, String encodedEmail, String email, Role role) {
        this.nickname = nickname;
        this.age = age;
        this.sex = sex;
        this.location = location;
        this.description = description;
        this.pictureIndex = pictureIndex;
        this.isOnlineAvailable = isOnlineAvailable;
        this.isOfflineAvailable = isOfflineAvailable;
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
        this.isOnlineAvailable = userProfileUpdateDto.isOnlineAvailable();
        this.isOfflineAvailable = userProfileUpdateDto.isOfflineAvailable();
    }
}
