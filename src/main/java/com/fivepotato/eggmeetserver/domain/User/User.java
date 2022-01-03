package com.fivepotato.eggmeetserver.domain.User;

import com.fivepotato.eggmeetserver.domain.Mentoring.MenteeArea;
import com.fivepotato.eggmeetserver.domain.Mentoring.MentorArea;
import com.fivepotato.eggmeetserver.dto.Mentoring.MentorAreaDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private byte age;

    @NotNull
    private Sex sex;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Location location;

    private String description;

    @NotNull
    private byte pictureIndex;

    @NotNull
    private boolean isOnlineAvailable;

    @NotNull
    private boolean isOfflineAvailable;

    @OneToOne(mappedBy = "mentor", orphanRemoval = true)
    private MentorArea mentorArea;

    @OneToOne(mappedBy = "mentee", orphanRemoval = true)
    private MenteeArea menteeArea;

    @NotNull
    private float mentorGrade = 0.0f;

    @NotNull
    private float menteeGrade = 0.0f;

    @NotNull
    private int point = 0;

    // chats, messages, reporterReports, reporteeReports, blacklistUsers

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
    public User(String name, byte age, Sex sex, String phoneNumber, Location location, String description, byte pictureIndex, boolean isOnlineAvailable, boolean isOfflineAvailable, LoginType loginType, String encodedEmail, String email, Role role) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
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
}
