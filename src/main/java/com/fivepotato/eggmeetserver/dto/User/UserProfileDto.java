package com.fivepotato.eggmeetserver.dto.user;

import com.fivepotato.eggmeetserver.domain.mentoring.Category;
import com.fivepotato.eggmeetserver.domain.user.Location;
import com.fivepotato.eggmeetserver.domain.user.Sex;
import com.fivepotato.eggmeetserver.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserProfileDto {

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
    private Integer mentorGrowthCost;
    private Category menteeCategory;
    private String menteeDescription;
    private float mentorRating;
    private float menteeRating;

    public UserProfileDto(User user) {
        this.nickname = user.getNickname();
        this.age = user.getAge();
        this.sex = user.getSex();
        this.location = user.getLocation();
        this.description = user.getDescription();
        this.pictureIndex = user.getPictureIndex();
        this.isOnlineAvailable = user.isOnlineAvailable();
        this.isOfflineAvailable = user.isOfflineAvailable();
        this.mentorCategory = user.getMentorArea().getCategory();
        this.mentorDescription = user.getMentorArea().getDescription();
        this.mentorCareer = user.getMentorArea().getCareer();
        this.mentorLink = user.getMentorArea().getLink();
        this.mentorGrowthCost = user.getMentorArea().getGrowthCost();
        this.menteeCategory = user.getMenteeArea().getCategory();
        this.menteeDescription = user.getMenteeArea().getDescription();
        this.mentorRating = user.getMentorRating();
        this.menteeRating = user.getMenteeRating();
    }
}
