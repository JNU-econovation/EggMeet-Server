package com.fivepotato.eggmeetserver.dto.user;

import com.fivepotato.eggmeetserver.domain.mentoring.Category;
import com.fivepotato.eggmeetserver.domain.user.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserProfileUpdateDto {

    private String nickname;
    private Location location;
    private String description;
    private int pictureIndex;
    private boolean isOnlineAvailable;
    private boolean isOfflineAvailable;

    private Category mentorCategory;
    private String mentorDescription;
    private String mentorCareer;
    private String mentorLink;
    private Integer mentorGrowthCost;
    private Category menteeCategory;
    private String menteeDescription;

    @Builder
    public UserProfileUpdateDto(String nickname, int age, Location location, String description, int pictureIndex, boolean isOnlineAvailable, boolean isOfflineAvailable, Category mentorCategory, String mentorDescription, String mentorCareer, String mentorLink, Integer mentorGrowthCost, Category menteeCategory, String menteeDescription) {
        this.nickname = nickname;
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
    }
}
