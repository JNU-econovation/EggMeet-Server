package com.fivepotato.eggmeetserver.domain.user;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String userId;

    @NotNull
    private String token;

    @Builder
    public RefreshToken(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
