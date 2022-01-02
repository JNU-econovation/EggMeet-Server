package com.fivepotato.eggmeetserver.service.Mentoring;

import com.fivepotato.eggmeetserver.domain.Mentoring.MenteeAreaRepository;
import com.fivepotato.eggmeetserver.domain.Mentoring.MentorAreaRepository;
import com.fivepotato.eggmeetserver.dto.Mentoring.MenteeAreaDto;
import com.fivepotato.eggmeetserver.dto.Mentoring.MentorAreaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentoringService {

    private final MentorAreaRepository mentorAreaRepository;
    private final MenteeAreaRepository menteeAreaRepository;

    public void setMentorArea(MentorAreaDto mentorAreaDto) {
        mentorAreaRepository.save(mentorAreaDto.toEntity());
    }

    public void setMenteeArea(MenteeAreaDto menteeAreaDto) {
        menteeAreaRepository.save(menteeAreaDto.toEntity());
    }
}
