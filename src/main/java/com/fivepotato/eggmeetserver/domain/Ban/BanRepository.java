package com.fivepotato.eggmeetserver.domain.Ban;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BanRepository extends JpaRepository<Ban, Long> {

    public boolean existsByBannedEmail(String bannedEmail);
}
