package com.fivepotato.eggmeetserver.dto.Mentoring;

import com.fivepotato.eggmeetserver.domain.Mentoring.Category;
import com.fivepotato.eggmeetserver.domain.User.Location;
import com.fivepotato.eggmeetserver.domain.User.Sex;
import com.fivepotato.eggmeetserver.domain.User.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MentorDto {

    private String name;
    private float mentorRating;
    private int growthPoint;
    private Category category;
    private Location location;
    private boolean isOnlineAvailable;
    private boolean isOfflineAvailable;
    private int age;
    private Sex sex;

    public MentorDto(User user) {
        this.name = user.getName();
        this.mentorRating = user.getMentorRating();
        this.growthPoint = user.getMentorArea().getGrowthPoint();
        this.category = user.getMentorArea().getCategory();
        this.location = user.getLocation();
        this.isOnlineAvailable = user.isOnlineAvailable();
        this.isOfflineAvailable = user.isOfflineAvailable();
        this.age = user.getAge();
        this.sex = user.getSex();
    }
}
