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
public class MenteeArea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "mentee_id")
    private User mentee;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;

    @Builder
    public MenteeArea(User mentee, Category category, String description) {
        this.mentee = mentee;
        this.category = category;
        this.description = description;
    }

    public void updateMenteeAreaByUserProfileUpdateDto(UserProfileUpdateDto userProfileUpdateDto) {
        this.category = userProfileUpdateDto.getMenteeCategory();
        this.description = userProfileUpdateDto.getMenteeDescription();
    }
}
