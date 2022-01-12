package com.fivepotato.eggmeetserver.service.Mentoring;

import com.fivepotato.eggmeetserver.domain.Mentoring.MenteeArea;
import com.fivepotato.eggmeetserver.domain.Mentoring.MenteeAreaRepository;
import com.fivepotato.eggmeetserver.domain.Mentoring.MentorArea;
import com.fivepotato.eggmeetserver.domain.Mentoring.MentorAreaRepository;
import com.fivepotato.eggmeetserver.dto.Mentoring.MenteeAreaDto;
import com.fivepotato.eggmeetserver.dto.Mentoring.MentorAreaDto;
import com.fivepotato.eggmeetserver.dto.User.UserProfileUpdateDto;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public MentorArea getMentorAreaByMentorId(Long mentorId) {
        return mentorAreaRepository.findByMentor_Id(mentorId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_MENTOR_AREA_BY_USER + mentorId));
    }

    public MenteeArea getMenteeAreaByMentorId(Long menteeId) {
        return menteeAreaRepository.findByMentee_Id(menteeId)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.NO_MENTEE_AREA_BY_USER + menteeId));
    }

    @Transactional
    public void updateMentorAreaByMentorId(Long mentorId, UserProfileUpdateDto userProfileUpdateDto) {
        try {
            MentorArea mentorArea = getMentorAreaByMentorId(mentorId);
            mentorArea.updateMentorAreaByUserProfileUpdateDto(userProfileUpdateDto);
        } catch (IllegalArgumentException ignored) {

        }
    }

    @Transactional
    public void updateMenteeAreaByMenteeId(Long menteeId, UserProfileUpdateDto userProfileUpdateDto) {
        try {
            MenteeArea menteeArea = getMenteeAreaByMentorId(menteeId);
            menteeArea.updateMenteeAreaByUserProfileUpdateDto(userProfileUpdateDto);
        } catch (IllegalArgumentException ignored) {

        }
    }
}
