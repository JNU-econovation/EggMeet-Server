package com.fivepotato.eggmeetserver.service.mentoring;

import com.fivepotato.eggmeetserver.domain.mentoring.MeetingRepository;
import com.fivepotato.eggmeetserver.domain.mentoring.Mentoring;
import com.fivepotato.eggmeetserver.domain.mentoring.MentoringRepository;
import com.fivepotato.eggmeetserver.domain.user.User;
import com.fivepotato.eggmeetserver.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MentoringService {

    private final MentoringRepository mentoringRepository;
    private final MeetingRepository meetingRepository;
    private final UserService userService;

    public void createMentoring(Long menteeId, Long mentorId) {
        User mentee = userService.getUserByUserId(menteeId);
        User mentor = userService.getUserByUserId(mentorId);
        mentoringRepository.save(new Mentoring(mentee, mentor));
    }
}
