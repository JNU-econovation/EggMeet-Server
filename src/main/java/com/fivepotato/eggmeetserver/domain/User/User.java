package com.fivepotato.eggmeetserver.domain.User;

import com.fivepotato.eggmeetserver.domain.Mentoring.MenteeArea;
import com.fivepotato.eggmeetserver.domain.Mentoring.MentorArea;
import com.sun.istack.NotNull;
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
    private String name;

    @NotNull
    private byte age;

    @NotNull
    private Sex sex;

    @NotNull
    private String phoneNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Do locationDo;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Dong locationDong;

    private String description;

    @NotNull
    private byte pictureIndex;

    @OneToOne(mappedBy = "mentor", orphanRemoval = true)
    private MentorArea mentorArea;

    @OneToOne(mappedBy = "mentee", orphanRemoval = true)
    private MenteeArea menteeArea;

    @NotNull
    private float mentorGrade;

    @NotNull
    private float menteeGrade;

    @NotNull
    private int point;

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
}
