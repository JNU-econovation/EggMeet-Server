package com.fivepotato.eggmeetserver.dto.Mentoring;

import com.fivepotato.eggmeetserver.domain.Mentoring.Category;
import com.fivepotato.eggmeetserver.domain.User.Location;
import com.fivepotato.eggmeetserver.domain.User.Sex;
import com.fivepotato.eggmeetserver.domain.User.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MenteeDto {

    private String name;
    private float menteeRating;
    private Category category;
    private Location location;
    private boolean isOnlineAvailable;
    private boolean isOfflineAvailable;
    private int age;
    private Sex sex;

    public MenteeDto(User user) {
        this.name = user.getName();
        this.menteeRating = user.getMenteeRating();
        this.category = user.getMenteeArea().getCategory();
        this.location = user.getLocation();
        this.isOnlineAvailable = user.isOnlineAvailable();
        this.isOfflineAvailable = user.isOfflineAvailable();
        this.age = user.getAge();
        this.sex = user.getSex();
    }


}
