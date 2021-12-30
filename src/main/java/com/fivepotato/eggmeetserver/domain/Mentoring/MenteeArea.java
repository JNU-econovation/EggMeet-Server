package com.fivepotato.eggmeetserver.domain.Mentoring;

import com.fivepotato.eggmeetserver.domain.User.User;
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
    private Category category;

    private String description;

    @Builder
    public MenteeArea(User mentee, Category category, String description) {
        this.mentee = mentee;
        this.category = category;
        this.description = description;
    }
}
