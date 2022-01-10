package com.fivepotato.eggmeetserver.dto.Mentoring;

import com.fivepotato.eggmeetserver.domain.Mentoring.Category;
import com.fivepotato.eggmeetserver.domain.User.Location;
import com.fivepotato.eggmeetserver.domain.User.Sex;
import lombok.Builder;
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

    @Builder
    public MenteeDto(String name, float menteeRating, Category category, Location location, boolean isOnlineAvailable, boolean isOfflineAvailable, int age, Sex sex) {
        this.name = name;
        this.menteeRating = menteeRating;
        this.category = category;
        this.location = location;
        this.isOnlineAvailable = isOnlineAvailable;
        this.isOfflineAvailable = isOfflineAvailable;
        this.age = age;
        this.sex = sex;
    }
}
