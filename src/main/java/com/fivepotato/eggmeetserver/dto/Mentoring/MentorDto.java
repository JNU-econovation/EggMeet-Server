package com.fivepotato.eggmeetserver.dto.mentoring;

import com.fivepotato.eggmeetserver.domain.mentoring.Category;
import com.fivepotato.eggmeetserver.domain.user.Location;
import com.fivepotato.eggmeetserver.domain.user.Sex;
import com.fivepotato.eggmeetserver.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MentorDto {

    private Long id;
    private String nickname;
    private float mentorRating;
    private int growthPoint;
    private Category category;
    private Location location;
    private boolean isOnlineAvailable;
    private boolean isOfflineAvailable;
    private int age;
    private Sex sex;

    public MentorDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
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
