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
    private int pictureIndex;
    private int age;
    private Sex sex;
    private float mentorRating;
    private int growthCost;
    private Category category;
    private Location location;
    private boolean onlineAvailable;
    private boolean offlineAvailable;

    public MentorDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.pictureIndex = user.getPictureIndex();
        this.age = user.getAge();
        this.sex = user.getSex();
        this.mentorRating = user.getMentorRating();
        this.growthCost = user.getMentorArea().getGrowthCost();
        this.category = user.getMentorArea().getCategory();
        this.location = user.getLocation();
        this.onlineAvailable = user.isOnlineAvailable();
        this.offlineAvailable = user.isOfflineAvailable();
    }
}
