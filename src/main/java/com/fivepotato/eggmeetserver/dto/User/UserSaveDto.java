package com.fivepotato.eggmeetserver.dto.User;

import com.fivepotato.eggmeetserver.domain.Mentoring.Category;
import com.fivepotato.eggmeetserver.domain.User.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class UserSaveDto {

    private String name;
    private byte age;
    private Sex sex;
    private String phoneNumber;
    private Location location;
    private String description;
    private byte pictureIndex;

    private boolean isOnlineAvailable;
    private boolean isOfflineAvailable;
    private Category mentorCategory;
    private String mentorDescription;
    private String mentorCareer;
    private String mentorLink;
    private Byte mentorGrowthPoint;
    private Category menteeCategory;
    private String menteeDescription;

    private LoginType loginType;
    private String socialToken;
    private String email;
    private Role role;

    @Builder
    public UserSaveDto(String name, byte age, Sex sex, String phoneNumber, Location location, String description, byte pictureIndex, boolean isOnlineAvailable, boolean isOfflineAvailable, Category mentorCategory, String mentorDescription, String mentorCareer, String mentorLink, byte mentorGrowthPoint, Category menteeCategory, String menteeDescription, LoginType loginType, String socialToken, Role role) {
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
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
                .age(age)
                .sex(sex)
                .phoneNumber(phoneNumber)
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

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, email);
    }
}
