package com.fivepotato.eggmeetserver.domain.ban;

import com.fivepotato.eggmeetserver.domain.user.LoginType;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity
public class Ban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LoginType bannedLoginType;

    @NotNull
    private String bannedEmail;

    @NotNull
    private Reason reason;

    @Builder
    public Ban(LoginType bannedLoginType, String bannedEmail, Reason reason) {
        this.bannedLoginType = bannedLoginType;
        this.bannedEmail = bannedEmail;
        this.reason = reason;
    }
}
