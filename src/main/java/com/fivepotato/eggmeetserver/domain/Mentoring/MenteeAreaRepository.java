package com.fivepotato.eggmeetserver.domain.Mentoring;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenteeAreaRepository extends JpaRepository<MenteeArea, Long> {

    Optional<MenteeArea> findByMentee_Id(Long menteeId);
}
