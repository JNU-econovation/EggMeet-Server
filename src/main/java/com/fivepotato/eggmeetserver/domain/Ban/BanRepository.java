package com.fivepotato.eggmeetserver.domain.ban;

import com.fivepotato.eggmeetserver.domain.user.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanRepository extends JpaRepository<Ban, Long> {

    public boolean existsByBannedLoginTypeAndBannedEmail(LoginType bannedLoginType, String bannedEmail);
}
