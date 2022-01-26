package com.fivepotato.eggmeetserver.service.mentoring;

import com.fivepotato.eggmeetserver.domain.mentoring.Meeting;
import com.fivepotato.eggmeetserver.domain.mentoring.MeetingRepository;
import com.fivepotato.eggmeetserver.domain.mentoring.Mentoring;
import com.fivepotato.eggmeetserver.dto.mentoring.MeetingSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MeetingService {

    private MeetingRepository meetingRepository;
    private MentoringService mentoringService;

    @Transactional
    public Long createMeeting(Long mentoringId, MeetingSaveDto meetingSaveDto) {
        Mentoring mentoring = mentoringService.getMentoringById(mentoringId);
        Meeting meeting = meetingRepository.save(meetingSaveDto.toEntity());
        meeting.setMentoring(mentoring);

        return meeting.getId();
    }
}
