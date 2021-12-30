package com.fivepotato.eggmeetserver.dto.Mentoring;

import com.fivepotato.eggmeetserver.domain.Mentoring.Category;
import com.fivepotato.eggmeetserver.domain.Mentoring.MentorArea;
import com.fivepotato.eggmeetserver.domain.User.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MentorAreaDto {

    private User mentor;
    private Category category;
    private String description;
    private String career;
    private String link;
    private Byte growthPoint;

    @Builder
    public MentorAreaDto(User mentor, Category category, String description, String career, String link, Byte growthPoint) {
        this.mentor = mentor;
        this.category = category;
        this.description = description;
        this.career = career;
        this.link = link;
        this.growthPoint = growthPoint;
    }

    public MentorArea toEntity() {
        return MentorArea.builder()
                .mentor(mentor)
                .category(category)
                .description(description)
                .career(career)
                .link(link)
                .growthPoint(growthPoint)
                .build();
    }
}
