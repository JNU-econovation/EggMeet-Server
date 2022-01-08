package com.fivepotato.eggmeetserver.dto.User;

import com.fivepotato.eggmeetserver.domain.Mentoring.Category;
import com.fivepotato.eggmeetserver.domain.User.Location;
import com.fivepotato.eggmeetserver.domain.User.Sex;
import com.fivepotato.eggmeetserver.domain.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserProfileDto {

    private String name;
    private byte age;
    private Sex sex;
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
    private float mentorGrade;
    private float menteeGrade;

    public UserProfileDto(User user) {
        this.name = user.getName();
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
        this.mentorGrowthPoint = user.getMentorArea().getGrowthPoint();
        this.menteeCategory = user.getMenteeArea().getCategory();
        this.menteeDescription = user.getMenteeArea().getDescription();
        this.mentorGrade = user.getMentorGrade();
        this.menteeGrade = user.getMenteeGrade();
    }
}
