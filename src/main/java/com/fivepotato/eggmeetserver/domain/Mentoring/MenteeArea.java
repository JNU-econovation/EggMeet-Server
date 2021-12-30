package com.fivepotato.eggmeetserver.domain.Mentoring;

import com.fivepotato.eggmeetserver.domain.User.User;
import com.sun.istack.NotNull;
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

    @NotNull
    private Type type;

    private String description;

    private String career;

    private String link;

    @NotNull
    private byte growthPoint;

    @NotNull
    private boolean isAvailableOnline;

    @NotNull
    private boolean isAvailableOffline;
}
