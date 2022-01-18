package com.fivepotato.eggmeetserver.dto.mentoring;

import com.fivepotato.eggmeetserver.domain.mentoring.Category;
import com.fivepotato.eggmeetserver.domain.mentoring.MenteeArea;
import com.fivepotato.eggmeetserver.domain.user.User;
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
