package com.fivepotato.eggmeetserver.domain.Mentoring;

import com.fivepotato.eggmeetserver.domain.User.User;
import com.sun.istack.NotNull;
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

    @NotNull
    private Type type;

    private String description;

    @NotNull
    private boolean isAvailableOnline;

    @NotNull
    private boolean isAvailableOffline;
}
