package com.fivepotato.eggmeetserver.domain.mentoring;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MentoringRepository extends JpaRepository<Mentoring, Long> {

    boolean existsByMentee_IdAndMentor_Id(long menteeId, long mentorId);
}
