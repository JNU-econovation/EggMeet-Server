package com.fivepotato.eggmeetserver.service.mentoring;

import com.fivepotato.eggmeetserver.domain.chat.Chatroom;
import com.fivepotato.eggmeetserver.domain.mentoring.Mentoring;
import com.fivepotato.eggmeetserver.domain.mentoring.MentoringRepository;
import com.fivepotato.eggmeetserver.domain.user.User;
import com.fivepotato.eggmeetserver.exception.ErrorCode;
import com.fivepotato.eggmeetserver.exception.NoContentException;
import com.fivepotato.eggmeetserver.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MentoringService {

    private final MentoringRepository mentoringRepository;
    private final UserService userService;

    @Transactional
    public Long createMentoring(long menteeId, long mentorId, Chatroom chatroom) {
        User mentee = userService.getUserByUserId(menteeId);
        User mentor = userService.getUserByUserId(mentorId);
        Mentoring mentoring = mentoringRepository.save(new Mentoring(mentee, mentor));
        mentoring.setChatroom(chatroom);

        return mentoring.getId();
    }

    @Transactional
    public void acceptMentoringRequest(long mentoringId) {
        Mentoring mentoring = getMentoringById(mentoringId);
        mentoring.acceptMentoringRequest();
    }

    public void denyMentoringRequest(long mentoringId) {
        mentoringRepository.deleteById(mentoringId);
    }

    public Mentoring getMentoringById(Long mentoringId) {
        return mentoringRepository.findById(mentoringId)
                .orElseThrow(() -> new NoContentException(ErrorCode.NO_MENTORING_BY_MENTORINGID + mentoringId));
    }

    public long getChatroomIdByMentoringId(long mentoringId) {
        return getMentoringById(mentoringId).getChatroom().getId();
    }

    public boolean isExistsMentoring(long menteeId, long mentorId) {
        return mentoringRepository.existsByMentee_IdAndMentor_Id(menteeId, mentorId);
    }

    public boolean isMentoringMentorByMentoringId(long mentoringId, long userId) {
        Mentoring mentoring = getMentoringById(mentoringId);

        return mentoring.getMentor().getId().equals(userId);
    }

    public boolean isMentoringMenteeByMentoringId(long mentoringId, long userId) {
        Mentoring mentoring = getMentoringById(mentoringId);

        return mentoring.getMentee().getId().equals(userId);
    }
}
