package com.fivepotato.eggmeetserver.dto.mentoring;

import com.fivepotato.eggmeetserver.domain.mentoring.Category;
import com.fivepotato.eggmeetserver.domain.user.Location;
import com.fivepotato.eggmeetserver.domain.user.Sex;
import com.fivepotato.eggmeetserver.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MenteeDto {

    private Long id;
    private String nickname;
    private int pictureIndex;
    private int age;
    private Sex sex;
    private float menteeRating;
    private Category category;
    private Location location;
    private boolean onlineAvailable;
    private boolean offlineAvailable;

    public MenteeDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.pictureIndex = user.getPictureIndex();
        this.age = user.getAge();
        this.sex = user.getSex();
        this.menteeRating = user.getMenteeRating();
        this.category = user.getMenteeArea().getCategory();
        this.location = user.getLocation();
        this.onlineAvailable = user.isOnlineAvailable();
        this.offlineAvailable = user.isOfflineAvailable();
    }


}
