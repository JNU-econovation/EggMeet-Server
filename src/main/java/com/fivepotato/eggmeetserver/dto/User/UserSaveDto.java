package com.fivepotato.eggmeetserver.dto.user;

import com.fivepotato.eggmeetserver.domain.mentoring.Category;
import com.fivepotato.eggmeetserver.domain.user.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class UserSaveDto {

    private String nickname;
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
    private int mentorGrowthCost;
    private Category menteeCategory;
    private String menteeDescription;

    private LoginType loginType;
    private String socialToken;
    private String email;
    private Role role;

    public void setEmail(String email) {
        this.email = email;
    }

    @Builder
    public UserSaveDto(String nickname, int age, Sex sex, Location location, String description, int pictureIndex, boolean isOnlineAvailable, boolean isOfflineAvailable, Category mentorCategory, String mentorDescription, String mentorCareer, String mentorLink, int mentorGrowthCost, Category menteeCategory, String menteeDescription, LoginType loginType, String socialToken, Role role) {
        this.nickname = nickname;
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
        this.mentorGrowthCost = mentorGrowthCost;
        this.menteeCategory = menteeCategory;
        this.menteeDescription = menteeDescription;
        this.loginType = loginType;
        this.socialToken = socialToken;
        this.role = role;
    }

    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
                .nickname(nickname)
                .age(age)
                .sex(sex)
                .location(location)
                .description(description)
                .pictureIndex(pictureIndex)
                .isOnlineAvailable(isOnlineAvailable)
                .isOfflineAvailable(isOfflineAvailable)
                .loginType(loginType)
                .email(email)
                .encodedEmail(passwordEncoder.encode(email))
                .role(role)
                .build();
    }

    public SocialTokenDto toSocialTokenDto() {
        return SocialTokenDto.builder()
                .loginType(loginType)
                .socialToken(socialToken)
                .build();
    }
}
