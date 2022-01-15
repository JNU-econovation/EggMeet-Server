package com.fivepotato.eggmeetserver.domain.Ban;

import com.fivepotato.eggmeetserver.domain.User.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanRepository extends JpaRepository<Ban, Long> {

    public boolean existsByBannedLoginTypeAndBannedEmail(LoginType bannedLoginType, String bannedEmail);
}
