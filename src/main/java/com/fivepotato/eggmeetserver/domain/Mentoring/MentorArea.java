package com.fivepotato.eggmeetserver.domain.mentoring;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fivepotato.eggmeetserver.domain.user.User;
import com.fivepotato.eggmeetserver.dto.user.UserProfileUpdateDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class MentorArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "mentor_id")
    private User mentor;

    @NotNull
    private Category category;

    private String description;

    private String career;

    private String link;

    @NotNull
    private int growthCost;

    @Builder
    public MentorArea(User mentor, Category category, String description, String career, String link, int growthCost) {
        this.mentor = mentor;
        this.category = category;
        this.description = description;
        this.career = career;
        this.link = link;
        this.growthCost = growthCost;
    }

    public void updateMentorAreaByUserProfileUpdateDto(UserProfileUpdateDto userProfileUpdateDto) {
        this.category = userProfileUpdateDto.getMentorCategory();
        this.description = userProfileUpdateDto.getMentorDescription();
        this.career = userProfileUpdateDto.getMentorCareer();
        this.link = userProfileUpdateDto.getMentorLink();
    }
}
