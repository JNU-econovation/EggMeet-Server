package com.fivepotato.eggmeetserver.dto.User;

import com.fivepotato.eggmeetserver.domain.Mentoring.Category;
import com.fivepotato.eggmeetserver.domain.User.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class UserSaveDto {

    private String name;
    private int age;
    private Sex sex;
    private Location location;
    private String description;
    private int pictureIndex;

    private boolean isOnlineAvailable;
    private boolean isOfflineAvailable;
    private Category mentorCategory;
    private String mentorDescription;
    private String mentorCareer;
    private String mentorLink;
    private int mentorGrowthPoint;
    private Category menteeCategory;
    private String menteeDescription;

    private LoginType loginType;
    private String socialToken;
    private String email;
    private Role role;

    @Builder
    public UserSaveDto(String name, int age, Sex sex, Location location, String description, int pictureIndex, boolean isOnlineAvailable, boolean isOfflineAvailable, Category mentorCategory, String mentorDescription, String mentorCareer, String mentorLink, int mentorGrowthPoint, Category menteeCategory, String menteeDescription, LoginType loginType, String socialToken, Role role) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.location = location;
        this.description = description;
        this.pictureIndex = pictureIndex;
        this.isOnlineAvailable = isOnlineAvailable;
        this.isOfflineAvailable = isOfflineAvailable;
        this.mentorCategory = mentorCategory;
        this.mentorDescription = mentorDescription;
        this.mentorCareer = mentorCareer;
        this.mentorLink = mentorLink;
        this.mentorGrowthPoint = mentorGrowthPoint;
        this.menteeCategory = menteeCategory;
        this.menteeDescription = menteeDescription;
        this.loginType = loginType;
        this.socialToken = socialToken;
        this.role = role;
    }

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .name(name)
                .age((byte) age)
                .sex(sex)
                .location(location)
                .description(description)
                .pictureIndex((byte) pictureIndex)
                .isOnlineAvailable(isOnlineAvailable)
                .isOfflineAvailable(isOfflineAvailable)
                .loginType(loginType)
                .email(email)
                .encodedEmail(passwordEncoder.encode(email))
                .role(role)
                .build();
    }
}
