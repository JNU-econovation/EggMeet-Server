package com.fivepotato.eggmeetserver.dto.Mentoring;

import com.fivepotato.eggmeetserver.domain.Mentoring.Category;
import com.fivepotato.eggmeetserver.domain.Mentoring.MenteeArea;
import com.fivepotato.eggmeetserver.domain.User.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenteeAreaDto {

    private User mentee;
    private Category category;
    private String description;

    @Builder
    public MenteeAreaDto(User mentee, Category category, String description) {
        this.mentee = mentee;
        this.category = category;
        this.description = description;
    }

    public MenteeArea toEntity() {
        return MenteeArea.builder()
                .mentee(mentee)
                .category(category)
                .description(description)
                .build();
    }
}
