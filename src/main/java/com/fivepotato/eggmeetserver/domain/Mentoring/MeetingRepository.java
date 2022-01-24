package com.fivepotato.eggmeetserver.domain.mentoring;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
