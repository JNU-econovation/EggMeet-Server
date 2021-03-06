package com.fivepotato.eggmeetserver.domain.mentoring;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MentorAreaRepository extends JpaRepository<MentorArea, Long> {

    Optional<MentorArea> findByMentor_Id(Long mentorId);
}
