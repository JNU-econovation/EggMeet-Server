package com.fivepotato.eggmeetserver.domain.Mentoring;

import com.fivepotato.eggmeetserver.domain.User.User;
import com.fivepotato.eggmeetserver.dto.User.UserProfileUpdateDto;
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
    private int growthPoint;

    @Builder
    public MentorArea(User mentor, Category category, String description, String career, String link, int growthPoint) {
        this.mentor = mentor;
        this.category = category;
        this.description = description;
        this.career = career;
        this.link = link;
        this.growthPoint = growthPoint;
    }

    public void updateMentorAreaByUserProfileUpdateDto(UserProfileUpdateDto userProfileUpdateDto) {
        this.category = userProfileUpdateDto.getMentorCategory();
        this.description = userProfileUpdateDto.getMentorDescription();
        this.career = userProfileUpdateDto.getMentorCareer();
        this.link = userProfileUpdateDto.getMentorLink();
    }
}
