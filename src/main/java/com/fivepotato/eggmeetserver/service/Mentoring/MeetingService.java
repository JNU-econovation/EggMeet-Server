package com.fivepotato.eggmeetserver.service.mentoring;

import com.fivepotato.eggmeetserver.domain.mentoring.Meeting;
import com.fivepotato.eggmeetserver.domain.mentoring.MeetingRepository;
import com.fivepotato.eggmeetserver.domain.mentoring.Mentoring;
import com.fivepotato.eggmeetserver.dto.mentoring.MeetingSaveDto;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import com.fivepotato.eggmeetserver.exception.NoContentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MeetingService {

    private MeetingRepository meetingRepository;
    private MentoringService mentoringService;

    @Transactional
    public long createMeeting(long mentoringId, MeetingSaveDto meetingSaveDto) {
        Mentoring mentoring = mentoringService.getMentoringById(mentoringId);
        Meeting meeting = meetingRepository.save(meetingSaveDto.toEntity());
        meeting.setMentoring(mentoring);

        return meeting.getId();
    }

    public Meeting getMeetingByMeetingId(long meetingId) {
        return meetingRepository.findById(meetingId)
                .orElseThrow(() -> new NoContentException(ErrorCode.NO_MEETING_BY_MEETINGID + meetingId));
    }

    public boolean isMeetingMentorByMentorId(long meetingId, long mentorId) {
        Meeting meeting = getMeetingByMeetingId(meetingId);

        return meeting.getMentoring().getMentor().getId().equals(mentorId);
    }

    @Transactional
    public void acceptMeetingRequest(long meetingId) {
        Meeting meeting = getMeetingByMeetingId(meetingId);
        meeting.acceptMeetingRequest();
    }

    public long getChatroomIdByMeetingId(long meetingId) {
        return getMeetingByMeetingId(meetingId).getMentoring().getChatroom().getId();
    }
}
