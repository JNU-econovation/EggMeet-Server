package com.fivepotato.eggmeetserver.domain.mentoring;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Meeting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private boolean isPending = false;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "mentoring_id")
    private Mentoring mentoring;

    @NotNull
    private LocalDateTime dateTime;
}
