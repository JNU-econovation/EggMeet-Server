package com.fivepotato.eggmeetserver.dto.mentoring;

import com.fivepotato.eggmeetserver.domain.mentoring.Category;
import com.fivepotato.eggmeetserver.domain.mentoring.MentorArea;
import com.fivepotato.eggmeetserver.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MentorAreaDto {

    private User mentor;
    private Category category;
    private String description;
    private String career;
    private String link;
    private int growthCost;

    @Builder
    public MentorAreaDto(User mentor, Category category, String description, String career, String link, int growthCost) {
        this.mentor = mentor;
        this.category = category;
        this.description = description;
        this.career = career;
        this.link = link;
        this.growthCost = growthCost;
    }

    public MentorArea toEntity() {
        return MentorArea.builder()
                .mentor(mentor)
                .category(category)
                .description(description)
                .career(career)
                .link(link)
                .growthCost(growthCost)
                .build();
    }
}
